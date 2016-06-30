package com.luqili.power;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.luqili.service.UserService;

/***
 * 验证会员是否登录
 * 
 * @author Administrator
 * 
 */
public class UserLoginInterceptor implements HandlerInterceptor {
	@Autowired
	private UserService userService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String servletpath = request.getServletPath();// 访问路径
		if (servletpath.endsWith(".jsp")) {// 不拦截本身JSP的访问
			return true;
		}
		if(!userService.isLogin(request.getSession())){
			response.sendRedirect(request.getContextPath() + "/timeout.lu");// 重定向到超时页面
			return false;
		}
		if (servletpath.matches("^(/lg/){1}.*(.lu){1}.*$")) {
			int start = servletpath.indexOf("/lg/") + 4;
			int end = servletpath.indexOf("/", start);
			String rname = servletpath.substring(start, end);// 获得权限名
			if (userService.isRightOKByLogin(rname,request.getSession())) {
				return true;
			} else {
				response.sendRedirect(request.getContextPath() + "/notright.lu");// 权限不足
				return false;
			}
		}
		/* 非匹配的路径都为非法路径 */
		response.sendRedirect(request.getContextPath() + "/notright.lu");// 权限不足
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}
}
