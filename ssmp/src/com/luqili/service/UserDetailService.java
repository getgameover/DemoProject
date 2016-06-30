package com.luqili.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luqili.db.beans.base.UserDetail;
import com.luqili.db.dao.base.UserDetailMapper;

@Service
public class UserDetailService {
	@Autowired
	private UserDetailMapper userDetailMapper;
	
	/**
	 * 根据用户ID,获取一个用户信息
	 * @param userId
	 * @return
	 */
	public UserDetail getUserDetailByUserId(Integer userId){
		return userDetailMapper.selectByPrimaryKey(userId);
	}
	/**
	 * 更新或修改一个用户信息
	 * @param ud
	 */
	public void upUserDetail(UserDetail ud,boolean isnew){
		if(isnew){
			userDetailMapper.insert(ud);
		}else{
			userDetailMapper.updateByPrimaryKey(ud);
		}
	}
}
