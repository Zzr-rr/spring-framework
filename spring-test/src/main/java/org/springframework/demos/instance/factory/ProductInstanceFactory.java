package org.springframework.demos.instance.factory;

import org.springframework.demos.instance.bean.Product;
import org.springframework.stereotype.Component;

// ProductInstanceFactory.java
@Component // 工厂本身需要是Spring管理的Bean
public class ProductInstanceFactory {

	// 实例工厂方法
	public Product createDiscountProduct() {
		Product product = new Product();
		product.setName("实例工厂折扣产品");
		product.setPrice(49.99);
		return product;
	}

}