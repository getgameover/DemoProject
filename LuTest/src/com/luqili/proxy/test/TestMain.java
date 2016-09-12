package com.luqili.proxy.test;

import java.lang.reflect.Proxy;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class TestMain {

	public static void main(String[] args) {
		TestService ts = new TestServiceImpl();
		TestInvocationImpl ti = new TestInvocationImpl(ts);
		TestService proxy_ts=(TestService)Proxy.newProxyInstance(TestService.class.getClassLoader(), ts.getClass().getInterfaces(), ti);
		
		//System.out.println("结果："+proxy_ts.sayHello());
		Number n=NumberUtils.createNumber("0xaaa");
		System.out.println(n);
		System.out.println(NumberUtils.isDigits("125"));
		System.out.println(NumberUtils.isNumber("0"));
		
	}

}
