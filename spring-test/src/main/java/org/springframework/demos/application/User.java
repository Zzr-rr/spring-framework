package org.springframework.demos.application;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class User implements InitializingBean, DisposableBean {
	public void sayHello() {
		System.out.println("[Say Hello] Hello, I'm User");
	}

	@Loggable
	public void deleteUser() {
		System.out.println("[Delete User] Delete User.");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("User is destroyed");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("User is after PropertiesSet");
	}
}
