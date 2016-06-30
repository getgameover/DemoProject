package com.luqili.controller.logined.common;

import java.util.HashMap;
import java.util.Map;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.luqili.controller.BaseController;
import com.luqili.db.beans.base.Img;
import com.luqili.db.beans.base.UserDetail;
import com.luqili.service.ImgCodeService;
import com.luqili.service.ImgService;
import com.luqili.service.UserDetailService;
import com.luqili.tool.CheckInputTool;
import com.luqili.tool.ConstantFile;
import com.luqili.tool.LuException;
import com.luqili.tool.MenuTool;
import com.luqili.tool.PageResponse;
import com.luqili.tool.ParseJsonUtil;
import com.luqili.tool.ResponseStatusCode;
import com.luqili.tool.enums.FileType;
import com.luqili.tool.page.Page;
/**
 * 
 * @author luqili 2016年1月21日
 *
 */
@Controller
@RequestMapping(value="/lg/common",method=RequestMethod.POST)
public class CommonController extends BaseController {
	@Autowired
	private ImgCodeService imgCodeService;
	@Autowired
	private ImgService imgService;
	@Autowired
	private UserDetailService userDetailService;
	@RequestMapping("/timeout")
	public PageResponse timeOut(){
		userService.timeOut(session);
		PageResponse res = new PageResponse();
		res.setStatusCode(ResponseStatusCode.OUTTIME);
		return res;
	}
	/**
	 * 修改密码
	 * @param json
	 * @return
	 */
	@RequestMapping("/changepwd")
	public PageResponse changePwd(@RequestBody String json){
		String oldPassword=ParseJsonUtil.parseJson(json, "oldPassword", String.class);
		String newPassword=ParseJsonUtil.parseJson(json, "newPassword", String.class);
		String rnewPassword=ParseJsonUtil.parseJson(json, "rnewPassword", String.class);
		String imgcode=ParseJsonUtil.parseJson(json, "imgcode", String.class);
		if(StringUtils.isBlank(oldPassword)){
			throw new LuException("原密码不能为空");
		}
		CheckInputTool.checkPwd(newPassword);
		
		if(!newPassword.equals(rnewPassword)){
			throw new LuException("两次输入的新密码不一致");
		}
		if(!imgCodeService.checkOneCode(imgcode, session)){
			throw new LuException("验证码不正确");
		}
		userService.changePwd(getLoginUser(), oldPassword, rnewPassword);
		PageResponse res = new PageResponse();
		return res;
	}
	@RequestMapping("/selectusermsg")
	public PageResponse selectUserMsg(@RequestBody String json){
		Integer userId=this.getLoginUserId();
		UserDetail ud = userDetailService.getUserDetailByUserId(userId);
		String headImgUrl="";
		if(ud!=null){
			headImgUrl = imgService.getImgUrl(ud.getHeadImgId());
		}
		PageResponse response = new PageResponse();
		response.setResult(ud);
		response.put("headImgUrl", headImgUrl);
		return response;
	}
	/**
	 * 修改用户信息
	 * @param json
	 * @return
	 */
	@RequestMapping("/upusermsg")
	public PageResponse upUserMsg(@RequestBody String json){
		Integer headImgId=ParseJsonUtil.parseJson(json, "headImgId", Integer.class);
		String name=ParseJsonUtil.parseJson(json, "name", String.class);
		Integer sex=ParseJsonUtil.parseJson(json, "sex", Integer.class);
		String qq=ParseJsonUtil.parseJson(json, "qq", String.class);
		String phone=ParseJsonUtil.parseJson(json, "phone", String.class);
		String tel=ParseJsonUtil.parseJson(json, "tel", String.class);
		String idcard=ParseJsonUtil.parseJson(json, "idcard", String.class);
		String address=ParseJsonUtil.parseJson(json, "address", String.class);
		UserDetail ud = userDetailService.getUserDetailByUserId(this.getLoginUserId());
		boolean isnew =false;
		if(ud==null){
			ud=new UserDetail();
			ud.setUserId(this.getLoginUserId());
			isnew =true;
		}
		ud.setAddress(address);
		ud.setHeadImgId(headImgId);
		ud.setIdcard(idcard);
		ud.setName(name);
		ud.setQq(qq);
		ud.setPhone(phone);
		ud.setSex(sex);
		ud.setTel(tel);
		userDetailService.upUserDetail(ud,isnew);
		PageResponse res = new PageResponse();
		return res;
	}
	/**
	 * 加载系统基本信息
	 * @return
	 */
	@RequestMapping("/loadsysmsg")
	public PageResponse loadSysMsg(){
		PageResponse res = new PageResponse();
		Map<String, Object> result = new HashMap<>();
		result.put("menus", MenuTool.getMenus(this.getLoginUserRight()));
		result.put("username", userService.getLoginUserName(session));
		res.setResult(result);
		return res;
	}
	/**
	 * 获得文件列表信息
	 * @return
	 */
	@RequestMapping("/filelist")
	public PageResponse fileList(@RequestBody String json){
		Page page = Page.createPage(json);
		imgService.selectImgListPage(page, getLoginUserId());
		PageResponse res = new PageResponse();
		res.setResult(page);
		res.put("fileTypes", FileType.getFileTypsMsg());
		return res;
	}
	/**
	 * 上传文件
	 * @return
	 */
	@RequestMapping("/upfiles")
	public PageResponse upFile(@RequestBody String json)throws Exception{
		if(json.length()>ConstantFile.UpFileSize){
			throw new LuException("文件不能超过5M");
		}
		String file=ParseJsonUtil.parseJson(json, "file", String.class);
		String name=ParseJsonUtil.parseJson(json, "name", String.class);
		Integer imgType=ParseJsonUtil.parseJson(json, "fileType", Integer.class);
		String fileName=ParseJsonUtil.parseJson(json, "fileName", String.class);
		Img img=imgService.saveImg(imgType,file, name, fileName, getLoginUserId());
		PageResponse res = new PageResponse();
		res.setResult(img.getId());
		return res;
	}
	/**
	 * 修改文件
	 * @return
	 */
	@RequestMapping("/upfilemsg")
	public PageResponse upFileMsg(@RequestBody String json)throws Exception{
		Integer id=ParseJsonUtil.parseJson(json, "id", Integer.class);
		String name=ParseJsonUtil.parseJson(json, "name", String.class);
		Integer status=ParseJsonUtil.parseJson(json, "status", Integer.class);
		Img img=imgService.updateImg(id, name, status, getLoginUserId());
		PageResponse res = new PageResponse();
		res.setResult(img.getId());
		return res;
	}
	@RequestMapping("/delefilemsg")
	public PageResponse deleFileMsg(@RequestBody String json)throws Exception{
		Integer id=ParseJsonUtil.parseJson(json, "id", Integer.class);
		imgService.deleImg(id,getLoginUserId());
		PageResponse res = new PageResponse();
		res.setResult(id);
		return res;
	}
}
