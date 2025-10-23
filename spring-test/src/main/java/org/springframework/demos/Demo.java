package org.springframework.demos;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Demo {
	public static void main(String[] args) {
		// 先初始化容器，这里会初始化reader和scanner。
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		// 通过reader来注册相应的配置类，注册完成后必须调用refresh方法刷新上下文信息。
		context.register(Config.class);
		context.refresh();
		// 通过上下文中的内容获取相应的Bean实例，再调用相关方法。
		User user = context.getBean("user", User.class);
		user.sayHello();
	}
}
