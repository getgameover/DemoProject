package com.luqili.proxy.test;

public class TestServiceImpl implements TestService {

	@Override
	public String sayHello() {
		System.out.println("Hello Word!");
		return "Hello Word!";
	}

}
