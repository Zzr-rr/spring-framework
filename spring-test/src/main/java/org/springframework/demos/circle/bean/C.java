package org.springframework.demos.circle.bean;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component
public class C {

	public C() {
		System.out.println("C is initialized");
	}
}
