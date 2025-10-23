package org.springframework.demos.instance.factory;

import org.springframework.demos.instance.bean.Product;

public class ProductStaticFactory {
	public static Product createSpecialProduct() {
		Product product = new Product();
		product.setName("静态工厂生称产品");
		product.setPrice(99.99);
		return product;
	}
}
