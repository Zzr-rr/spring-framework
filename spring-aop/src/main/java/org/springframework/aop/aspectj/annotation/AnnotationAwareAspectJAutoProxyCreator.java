/*
 * Copyright 2002-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.aop.aspectj.annotation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.jspecify.annotations.Nullable;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.Assert;

/**
 * {@link AspectJAwareAdvisorAutoProxyCreator} subclass that processes all AspectJ
 * annotation aspects in the current application context, as well as Spring Advisors.
 * <p>
 * 这个类处理所有在当前应用下的AspectJ风格的注解切面，比如Spring的Advisors
 *
 * <p>Any AspectJ annotated classes will automatically be recognized, and their
 * advice applied if Spring AOP's proxy-based model is capable of applying it.
 * This covers method execution joinpoints.
 * <p>
 * 所有的使用了AspectJ注解的类都将被自动识别，并且，如果Spring的AOP的基础代理模型能够应用，他们的advice都将被应用。
 * 这包含了方法执行的切入点。
 *
 * <p>If the &lt;aop:include&gt; element is used, only @AspectJ beans with names matched by
 * an include pattern will be considered as defining aspects to use for Spring auto-proxying.
 * <p>
 * 如果使用了aop:include的元素，只有名称与该元素匹配的@AspecJ beans才会被包含。
 * 一个包含模式将被视为定义了用于Spring自动代理的方面。
 *
 * <p>Processing of Spring Advisors follows the rules established in
 * {@link org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator}.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @see org.springframework.aop.aspectj.annotation.AspectJAdvisorFactory
 * @since 2.0
 */
@SuppressWarnings("serial")
public class AnnotationAwareAspectJAutoProxyCreator extends AspectJAwareAdvisorAutoProxyCreator {

	/**
	 * 该类继承自AspectJAwareAdvisorAutoProxyCreator，表明它是Spring AOP自动代理机制的一部分
	 * 专门处理AspectJ风格的切面
	 */

	// 用于匹配符合条件的@AspectJ bean名称的正则表达式列表
	private @Nullable List<Pattern> includePatterns;

	// 用于创建AspectJ通知的工厂，同时作为容器，也可以把Bean统一管理起来，方便去进行扫描获取相应Bean
	private @Nullable AspectJAdvisorFactory aspectJAdvisorFactory;

	// 用于构建AspectJ通知的构造器
	private @Nullable BeanFactoryAspectJAdvisorsBuilder aspectJAdvisorsBuilder;

	// 设置用于匹配@AspectJ bean名称的正则表达式列表
	public void setIncludePatterns(List<String> patterns) {
		this.includePatterns = new ArrayList<>(patterns.size());
		for (String patternText : patterns) {
			// 将字符串模式编译为Pattern对象并存储在includePatterns中
			this.includePatterns.add(Pattern.compile(patternText));
		}
	}

	public void setAspectJAdvisorFactory(AspectJAdvisorFactory aspectJAdvisorFactory) {
		Assert.notNull(aspectJAdvisorFactory, "AspectJAdvisorFactory must not be null");
		this.aspectJAdvisorFactory = aspectJAdvisorFactory;
	}

	// 设置Spring容器
	@Override
	protected void initBeanFactory(ConfigurableListableBeanFactory beanFactory) {
		super.initBeanFactory(beanFactory);
		if (this.aspectJAdvisorFactory == null) {
			this.aspectJAdvisorFactory = new ReflectiveAspectJAdvisorFactory(beanFactory);
		}
		this.aspectJAdvisorsBuilder =
				new BeanFactoryAspectJAdvisorsBuilderAdapter(beanFactory, this.aspectJAdvisorFactory);
	}

	// 查找候选的通知器（通知器封装了通知(Advice)和切点(Pointcut)的组合）
	@Override
	protected List<Advisor> findCandidateAdvisors() {
		// 根据超类的规则，将所有发现的advisors都加进去
		List<Advisor> advisors = super.findCandidateAdvisors();
		// 为所有在当前bean工厂的AspectJ切面构建Advisors
		if (this.aspectJAdvisorsBuilder != null) {
			advisors.addAll(this.aspectJAdvisorsBuilder.buildAspectJAdvisors());
		}
		return advisors;
	}

	// 判断一个类是否是基础设施类
	@Override
	protected boolean isInfrastructureClass(Class<?> beanClass) {
		// Previously we setProxyTargetClass(true) in the constructor, but that has too
		// broad an impact. Instead we now override isInfrastructureClass to avoid proxying
		// aspects. I'm not entirely happy with that as there is no good reason not
		// to advise aspects, except that it causes advice invocation to go through a
		// proxy, and if the aspect implements, for example, the Ordered interface it will be
		// proxied by that interface and fail at runtime as the advice method is not
		// defined on the interface. We could potentially relax the restriction about
		// not advising aspects in the future.
		return (super.isInfrastructureClass(beanClass) ||
				(this.aspectJAdvisorFactory != null && this.aspectJAdvisorFactory.isAspect(beanClass)));
	}

	// 检查给定的切面bean是否符合自动代理条件
	protected boolean isEligibleAspectBean(String beanName) {
		if (this.includePatterns == null) {
			return true;
		} else {
			for (Pattern pattern : this.includePatterns) {
				if (pattern.matcher(beanName).matches()) {
					return true;
				}
			}
			return false;
		}
	}


	/**
	 * 这个内部类扩展了BeanFactoryAspectJAdvisorsBuilder，主要有两个作用：
	 * 1. 委托给外部类的isEligibleAspectBean方法来判断bean是否符合条件
	 * 2. 实现了模版方法模式，将eligibility判断逻辑委托给外部类
	 */
	private class BeanFactoryAspectJAdvisorsBuilderAdapter extends BeanFactoryAspectJAdvisorsBuilder {

		public BeanFactoryAspectJAdvisorsBuilderAdapter(
				ListableBeanFactory beanFactory, AspectJAdvisorFactory advisorFactory) {

			super(beanFactory, advisorFactory);
		}

		@Override
		protected boolean isEligibleBean(String beanName) {
			return AnnotationAwareAspectJAutoProxyCreator.this.isEligibleAspectBean(beanName);
		}
	}

}
