package com.luqili.controller.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.luqili.controller.BaseController;
import com.luqili.tool.PageResponse;
import com.luqili.tool.ResponseStatusCode;
/**
 * 处理部分页面
 * @author 46155
 *
 */
@Controller
public class IndexController extends BaseController {
	/**
	 * 将动态请求定向到静态页面上
	 * @return
	 */
	@RequestMapping(value="index",method=RequestMethod.GET)
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/view/mamage_main.html");
		return mv;
	}
	/**
	 * 登陆超时处理
	 * @return
	 */
	@RequestMapping(value="/timeout")
	public PageResponse timeOut(){
		PageResponse response = new PageResponse();
		response.setStatusCode(ResponseStatusCode.OUTTIME);
		response.setMessage("登陆超时,请重新登陆!");
		return response;
	}
	/**
	 * 登陆超时处理
	 * @return
	 */
	@RequestMapping(value="/notright")
	public PageResponse noRight(){
		PageResponse response = new PageResponse();
		response.setStatusCode(ResponseStatusCode.NOTRIGHT);
		response.setMessage("401 未授权!");
		return response;
	}
	@RequestMapping(value="/error-404")
	public PageResponse error(){
		PageResponse response = new PageResponse();
		response.setStatusCode(ResponseStatusCode.NOTFIND);
		response.setMessage("404 未查询到相应请求处理方案!");
		return response;
	}
}
