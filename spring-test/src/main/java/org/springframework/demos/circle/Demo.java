package org.springframework.demos.circle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.demos.circle.bean.A;
import org.springframework.demos.circle.bean.C;

public class Demo {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		A a = (A) context.getBean("a");
		a.sayHello();
		context.getBean("c", C.class);
	}
}
