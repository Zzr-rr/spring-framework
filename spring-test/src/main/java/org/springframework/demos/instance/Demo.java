package org.springframework.demos.instance;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.demos.instance.bean.Product;
import org.springframework.demos.instance.config.AppConfig;

/**
 * Bean，主要有3种不同的实例化方式：
 * 1. 构造函数实例化
 * 2. 静态工厂方法实例化
 * 3. 实例工厂方法实例化
 */
public class Demo {
	public static void main(String[] args) {
		// 创建注解配置容器
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		// 获取Bean实例
		Product defaultProduct = ctx.getBean(Product.class);
		Product staticFactoryProduct = ctx.getBean("specialProduct", Product.class);
		Product instanceFactoryProduct = ctx.getBean("discountProduct", Product.class);

		// 输出结果
		System.out.println("1. 构造器实例化：" + defaultProduct);
		System.out.println("2. 静态工厂实例化：" + staticFactoryProduct);
		System.out.println("3. 实例工厂实例化：" + instanceFactoryProduct);
	}
}