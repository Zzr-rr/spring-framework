package org.springframework.demos.circle.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class B {

	@Autowired
	private A a;

	public void sayHello() {
		System.out.println("class B hashcode:" + this.hashCode());
	}

}
