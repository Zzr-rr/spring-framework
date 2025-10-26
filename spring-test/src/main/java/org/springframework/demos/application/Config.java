package org.springframework.demos.application;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@ComponentScan("org.springframework.demos")
@EnableAspectJAutoProxy // 启用@Aspect支持
public class Config {
}
