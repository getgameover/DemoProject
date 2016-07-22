package com.luqili.power;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;

import com.luqili.service.UserService;

@Aspect
public class ActionLogUtilAspect {
	@Autowired
	private UserService userService;
	@Autowired
	private HttpSession session;
	@Autowired
	private HttpServletRequest request;
	
	@Around("within(com.luqili.controller..*) && execution(public * *(..))")
	public Object beforeLog(ProceedingJoinPoint pjp) throws Throwable {
		
		long startTime=System.currentTimeMillis();
		Object result=pjp.proceed();
		String userName=userService.getLoginUserName(session);
		request.getParameterMap();
		long time=System.currentTimeMillis()-startTime;
		System.out.println("记录日志：\n耗时："+time
				+"\n用户："+userName
				+"\n路径："+request.getRequestURI()
				+"\nIP："+request.getRemoteAddr()
				+"\nIP："+request.getLocalAddr()
				+"\n执行方法："+pjp.getSignature().getDeclaringTypeName()+"."+pjp.getSignature().getName()
				+"\n写入参数："+pjp.getArgs());
		return result;
	}

}
