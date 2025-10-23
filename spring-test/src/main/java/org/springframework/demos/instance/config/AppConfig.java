package org.springframework.demos.instance.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.demos.instance.bean.Product;
import org.springframework.demos.instance.factory.ProductInstanceFactory;
import org.springframework.demos.instance.factory.ProductStaticFactory;

// 在配置类中启用扫描，通过@Component自动实例化
@Configuration
@ComponentScan("org.springframework.demos.instance")
public class AppConfig {

	@Autowired
	private ProductInstanceFactory productInstanceFactory;

	@Bean
	@Primary
	public Product defaultProduct() {
		return new Product();
	}


	@Bean(name = "specialProduct") // 通过静态工厂方法实例化
	public Product specialProduct() {
		return ProductStaticFactory.createSpecialProduct();
	}

	@Bean(name = "discountProduct")
	public Product discountProduct(ProductInstanceFactory factory) {
		return factory.createDiscountProduct();
	}

}
