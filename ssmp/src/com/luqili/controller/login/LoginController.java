package com.luqili.controller.login;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.luqili.controller.BaseController;
import com.luqili.db.beans.base.User;
import com.luqili.service.ImgCodeService;
import com.luqili.tool.LuException;
import com.luqili.tool.PageResponse;
import com.luqili.tool.ParseJsonUtil;

@Controller
@RequestMapping(value="/login/",method=RequestMethod.POST)
public class LoginController extends BaseController {
	@Autowired
	private ImgCodeService codeService;
	/**
	 * 登入系统
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/in")
	public PageResponse response(@RequestBody String json,HttpSession session)throws Exception{
		String username=ParseJsonUtil.parseJson(json, "username", String.class);
		String password=ParseJsonUtil.parseJson(json, "password", String.class);
		String imgcode=ParseJsonUtil.parseJson(json, "imgcode", String.class);
		if(!codeService.checkOneCode(imgcode, session)){
			throw new LuException("验证码错误");
		}
		User user=userService.loginUser(username, password, session);
		PageResponse response = new PageResponse(true);
		Map<String, Object> result = new HashMap<>();
		result.put("username", user.getUsername());
		response.setResult(result);
		return response;
	}
	
}
