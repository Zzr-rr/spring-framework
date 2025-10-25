package org.springframework.demos.proxy;

import org.springframework.aop.framework.AopContext;

public class UserServiceImpl implements UserService {
	@Override
	public void sayHello() {
		System.out.println("hello");
		UserService proxy = (UserService) AopContext.currentProxy();
		proxy.sayBye();
	}

	@Override
	public void sayBye() {
		System.out.println("bye");
	}
}
