package org.springframework.demos.circle.bean;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class A implements ApplicationContextAware {

	private ApplicationContext context;

	public A() {
		System.out.println("A is initialized");
	}

	public void sayHello() {
		B b = this.context.getBean("b", B.class);
		b.sayHello();
	}

	@Override
	public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

}
