package com.luqili.service;

import java.sql.Timestamp;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luqili.db.beans.base.User;
import com.luqili.db.beans.base.UserDetail;
import com.luqili.db.dao.UserDAO;
import com.luqili.db.dao.base.UserDetailMapper;
import com.luqili.db.dao.base.UserMapper;
import com.luqili.power.RightData;
import com.luqili.tool.CheckInputTool;
import com.luqili.tool.LuException;
import com.luqili.tool.SessionKey;
import com.luqili.tool.enums.Role;
import com.luqili.tool.enums.Status;
import com.luqili.tool.page.Page;
/**
 * 用户服务类
 * @author 46155
 *
 */
@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private UserDetailMapper userDetailMapper;
	
	public User getUserById(Integer id){
		return userMapper.selectByPrimaryKey(id);
	}
	
	public User getUserByUserName(String username){
		return userMapper.selectByUserName(username);
	}
	/**
	 * 获得当前登陆用户
	 * @param session
	 * @return
	 */
	public User getLoginUser(HttpSession session){
		Integer userid=(Integer)session.getAttribute(SessionKey.USER_ID);
		if(userid!=null){
			return this.getUserById(userid);
		}else{
			return null;
		}
	}
	/**
	 * 查看当前用户是否登陆
	 * @param session
	 * @return
	 */
	public boolean isLogin(HttpSession session){
		return this.getLoginUserId(session)!=null;
	}
	/**
	 * 获得当前登陆用户的权限
	 * @param session
	 * @return
	 */
	public Long getLoginUserRight(HttpSession session){
		return(Long)session.getAttribute(SessionKey.USER_RIGHT);
	}
	/**
	 * 获得当前登陆的用户名
	 * @param session
	 * @return
	 */
	public String getLoginUserName(HttpSession session){
		return(String)session.getAttribute(SessionKey.USER_NAME);
	}
	/**
	 * 获得当前登陆用户的ID
	 * @param session
	 * @return
	 */
	public Integer getLoginUserId(HttpSession session){
		return(Integer)session.getAttribute(SessionKey.USER_ID);
	}
	/**
	 * 验证当前用户是否有该权限
	 * @param rightName
	 * @param session
	 * @return
	 */
	public boolean isRightOKByLogin(String rightName,HttpSession session){
		Long rightValue=RightData.getRightValue(rightName);
		return isRightOKByLogin(rightValue, session);
	}
	/**
	 * 验证当前用户是否有该权限
	 * @param rightValue
	 * @param session
	 * @return
	 */
	public boolean isRightOKByLogin(Long rightValue,HttpSession session){
		Long right =this.getLoginUserRight(session);
		if(right==null||rightValue==null||right<1||rightValue<1){
			return false;
		}
		return (right&rightValue)==rightValue;
	}
	/**
	 * 进行登陆
	 * @param username
	 * @param password
	 * @param session
	 * @return
	 */
	public User loginUser(String username,String password,HttpSession session){
		User user = this.getUserByUserName(username);
		if(user==null){
			throw new LuException("账号或密码错误");
		}
		String pwd=this.getHandlPwd(password, user.getPwdKey());
		if(!pwd.equals(user.getPassword())){
			throw new LuException("账号或密码错误");
		}
		session.setAttribute(SessionKey.USER_ID, user.getId());
		session.setAttribute(SessionKey.USER_NAME, user.getUsername());
		session.setAttribute(SessionKey.USER_RIGHT, user.getUserRight());
		return user;
	}
	/**
	 * 退出登陆
	 * @param session
	 */
	public void timeOut(HttpSession session){
		session.removeAttribute(SessionKey.USER_ID);
		session.removeAttribute(SessionKey.USER_NAME);
		session.removeAttribute(SessionKey.USER_RIGHT);
		session.invalidate();
	}
	/**
	 * 修改密码
	 * @param user
	 * @param oldPassword
	 * @param newPassword
	 */
	public void changePwd(User user,String oldPassword,String newPassword){
		if(user==null){
			throw new LuException("未查询到用户信息");
		}
		String pwd=this.getHandlPwd(oldPassword, user.getPwdKey());
		if(!pwd.equals(user.getPassword())){
			throw new LuException("原密码不正确");
		}
		String key=Math.random()+"";
		String npwd=this.getHandlPwd(newPassword, key);
		user.setPassword(npwd);
		user.setPwdKey(key);
		userMapper.updateByPrimaryKeySelective(user);
	}
	/**
	 * 查询全部用户信息
	 * @param page
	 */
	public void selectUserListPage(Page page){
		userDAO.selectUserListPage(page);
	}
	@Transactional
	public User saveUser(String username,String password,String name,String phone,String address,Long userRight){
		CheckInputTool.checkUserName(username);
		CheckInputTool.checkPwd(password);
		User user=userMapper.selectByUserName(username);
		if(user!=null){
			throw new LuException("账户名已存在");
		}
		String key=Math.random()+"";
		String npwd=this.getHandlPwd(password, key);
		
		user=new User();
		user.setUsername(username);
		user.setPassword(npwd);
		user.setPwdKey(key);
		user.setUserRight(userRight);
		user.setAddTime(new Timestamp(System.currentTimeMillis()));
		user.setStatus(Status.Enabled.getStatusValue());
		user.setRole(Role.Client.getRoleValue());
		userMapper.insertSelective(user);
		UserDetail ud = new UserDetail();
		ud.setName(name);
		ud.setPhone(phone);
		ud.setAddress(address);
		ud.setUserId(user.getId());
		userDetailMapper.insert(ud);
		return user;
	}
	@Transactional
	public void upDateUser(Integer id,String username,String password,String name,String phone,String address,Long userRight){
		User user= userMapper.selectByPrimaryKey(id);
		if(user==null){
			throw new LuException("未查询到用户信息");
		}
		//用户名不一致时修改用户名
		if(!user.getUsername().equals(username)){
			CheckInputTool.checkUserName(username);
			User new_user=userMapper.selectByUserName(username);
			if(new_user!=null){
				throw new LuException("账户名已存在");
			}
			user.setUsername(username);
		}
		
		//密码不为空的时候重置密码
		if(StringUtils.isNoneBlank(password)){
			CheckInputTool.checkPwd(password);
			String key=Math.random()+"";
			String npwd=this.getHandlPwd(password, key);
			user.setPassword(npwd);
			user.setPwdKey(key);
		}
		if(!user.getUserRight().equals(userRight)){
			user.setUserRight(userRight);
		}
		
		userMapper.updateByPrimaryKeySelective(user);
		UserDetail ud = userDetailMapper.selectByPrimaryKey(user.getId());
		boolean isnew=false;
		if(ud==null){
			ud=new UserDetail();
			ud.setUserId(user.getId());
			isnew=true;
		}
		ud.setName(name);
		ud.setPhone(phone);
		ud.setAddress(address);
		if(isnew){
			userDetailMapper.insert(ud);
		}else{
			userDetailMapper.updateByPrimaryKeySelective(ud);
		}
		
	}
	
	
	private String getHandlPwd(String password,String key){
		String p1=DigestUtils.sha1Hex(password);
		return DigestUtils.sha1Hex(p1+key);
	}
	
}
