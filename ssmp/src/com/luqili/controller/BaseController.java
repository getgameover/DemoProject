package com.luqili.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.luqili.db.beans.base.User;
import com.luqili.service.UserService;
import com.luqili.tool.LuException;
import com.luqili.tool.PageResponse;

/**
 * 
 * @author 46155
 *
 */

public abstract class BaseController {
	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected HttpSession session;
	@Autowired
	protected UserService userService;
	
	public User getLoginUser(){
		return userService.getLoginUser(session);
	}
	public Integer getLoginUserId(){
		return userService.getLoginUserId(session);
	}
	public Long getLoginUserRight(){
		return userService.getLoginUserRight(session);
	}
	/**
	 * 统一处理异常问题
	 * @param luException
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value=LuException.class)
	public PageResponse handleLuException(LuException luException,HttpServletRequest request){
		PageResponse re = new PageResponse(false);
		re.setMessage(luException.getMessage());
		return re;
	}
	/**
	 * 统一处理系统异常
	 * @param exception
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value=Exception.class)
	public PageResponse handleException(Exception exception,HttpServletRequest request){
		PageResponse re = new PageResponse(false);
		re.setMessage("内部遇到错误:"+exception.getMessage());
		exception.printStackTrace();
		return re;
	}
}
