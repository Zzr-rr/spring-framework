package org.springframework.demos.circle.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class A {

	@Autowired
	private B b;

	public A() {
		System.out.println("A is initialized");
	}

	public void sayHello() {
		System.out.println("Hello:" + b);
	}
}
