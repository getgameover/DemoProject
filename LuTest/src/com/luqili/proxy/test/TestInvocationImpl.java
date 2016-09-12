package com.luqili.proxy.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TestInvocationImpl implements InvocationHandler {
	private Object target;
	public TestInvocationImpl(Object target){
		this.target=target;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("--准备说话");
		Object obj=method.invoke(target, args);
		System.out.println("--完成说话");
		return obj;
	}

}
