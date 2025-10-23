package org.springframework.demos.application;

import org.springframework.stereotype.Component;

@Component
public class User {
	public void sayHello() {
		System.out.println("Hello, I'm User");
	}
}
