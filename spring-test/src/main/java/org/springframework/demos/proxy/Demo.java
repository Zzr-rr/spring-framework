package org.springframework.demos.proxy;

import org.springframework.aop.framework.ProxyFactory;

public class Demo {
	public static void main(String[] args) {
		UserService service = new UserServiceImpl();

		ProxyFactory factory = new ProxyFactory();
		factory.setTarget(service);
		factory.addInterface(UserService.class);
		factory.addAdvice(new LoggingAdvice());
		factory.setExposeProxy(true);

		UserService proxy = (UserService) factory.getProxy();
		proxy.sayHello();
	}
}
