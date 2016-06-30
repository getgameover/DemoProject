package com.luqili.controller.logined.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.luqili.controller.BaseController;
import com.luqili.power.RightData;
import com.luqili.tool.PageResponse;
import com.luqili.tool.ParseJsonUtil;
import com.luqili.tool.page.Page;
@Controller
@RequestMapping(value="/lg/system",method=RequestMethod.POST)
public class ManageUserListController extends BaseController{
	@RequestMapping("/userlist")
	public PageResponse userList(@RequestBody String json){
		List<Map<String, Object>> rights = RightData.getRightMsgAll();
		Page page = Page.createPage(json);
		userService.selectUserListPage(page);
		formatRightHtml(page.getData());
		PageResponse res = new PageResponse();
		res.setResult(page);
		res.put("rights", rights);
		return res;
	}
	@RequestMapping("/saveuser")
	public PageResponse saveUser(@RequestBody String json){
		String username=ParseJsonUtil.parseJson(json, "username", String.class);
		String password=ParseJsonUtil.parseJson(json, "password", String.class);
		String name=ParseJsonUtil.parseJson(json, "name", String.class);
		String phone=ParseJsonUtil.parseJson(json, "phone", String.class);
		String address=ParseJsonUtil.parseJson(json, "address", String.class);
		HashMap<String, String> mapRightnames=ParseJsonUtil.parseJson(json, "rights", HashMap.class);
		long userRight=0;
		if(mapRightnames!=null){
			List<String> rightNames=new ArrayList<>(mapRightnames.values());
			userRight= RightData.getRightValueByRightNames(rightNames);
		}
		userService.saveUser(username, password, name, phone, address, userRight);
		Page page = Page.createPage(json);
		PageResponse res = new PageResponse();
		res.setResult(page);
		return res;
	}
	@RequestMapping("/updateuser")
	public PageResponse upDateUser(@RequestBody String json){
		Integer id=ParseJsonUtil.parseJson(json, "id", Integer.class);
		String username=ParseJsonUtil.parseJson(json, "username", String.class);
		String password=ParseJsonUtil.parseJson(json, "password", String.class);
		String phone=ParseJsonUtil.parseJson(json, "phone", String.class);
		String address=ParseJsonUtil.parseJson(json, "address", String.class);
		String name=ParseJsonUtil.parseJson(json, "name", String.class);
		HashMap<String, String> mapRightnames=ParseJsonUtil.parseJson(json, "rights", HashMap.class);
		long userRight=0;
		if(mapRightnames!=null){
			List<String> rightNames=new ArrayList<>(mapRightnames.values());
			userRight= RightData.getRightValueByRightNames(rightNames);
		}
		
		userService.upDateUser(id, username, password, name, phone, address, userRight);
		Page page = Page.createPage(json);
		PageResponse res = new PageResponse();
		res.setResult(page);
		return res;
	}
	private void formatRightHtml(List<Map<String, Object>> ltuser){
		for(Map<String, Object> userMap:ltuser){
			long right = (Long)userMap.get("userRight");
			StringBuffer rightHtml=new StringBuffer();
			List<Map<String, Object>> rightsMsg = RightData.getRightMsg(right);
			for(Map<String, Object> rd:rightsMsg){
				rightHtml.append("<span class=\"lu_format lu_format_right\" title=\"");
				rightHtml.append(rd.get("description"));
				rightHtml.append("\" >");
				rightHtml.append(rd.get("name"));
				rightHtml.append("</span>");
			}
			userMap.put("rightHtml", rightHtml);
			userMap.put("rightsMsg", rightsMsg);
		}
	}
}
