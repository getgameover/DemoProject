package com.luqili.service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.luqili.tool.SessionKey;

/***
 * 验证码服务类
 * @author lu
 * 2015-7-5 上午09:20:14
 */
@Service
public class ImgCodeService {
	public void saveCode(String code,HttpSession session){
		session.setAttribute(SessionKey.IMG_CODE, code.toUpperCase());
	}
	/**
	 * 可多次验证
	 * @author lu
	 * 2015-7-5 上午09:23:40
	 * @param code
	 * @param session
	 * @return
	 */
	public boolean checkCode(String code,HttpSession session){
		if(code==null){
			return false;
		}
		return code.toUpperCase().equals(session.getAttribute(SessionKey.IMG_CODE));
	}
	/**
	 * 只允许验证一次
	 * @author lu
	 * 2015-7-5 上午09:25:36
	 * @param code
	 * @param session
	 * @return
	 */
	public boolean checkOneCode(String code,HttpSession session){
		String scode=(String)session.getAttribute(SessionKey.IMG_CODE);
		session.removeAttribute(SessionKey.IMG_CODE);
		if(code==null){
			return false;
		}
		return code.toUpperCase().equals(scode);
	}
	
}
