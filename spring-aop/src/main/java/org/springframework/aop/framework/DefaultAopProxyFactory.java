/*
 * Copyright 2002-2015 the original author or authors.
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

package org.springframework.aop.framework;

import org.springframework.aop.SpringProxy;

import java.io.Serializable;
import java.lang.reflect.Proxy;

/**
 * Default {@link AopProxyFactory} implementation, creating either a CGLIB proxy
 * or a JDK dynamic proxy.
 *
 * <p>Creates a CGLIB proxy if one the following is true for a given
 * {@link AdvisedSupport} instance:
 * <ul>
 * <li>the {@code optimize} flag is set
 * <li>the {@code proxyTargetClass} flag is set
 * <li>no proxy interfaces have been specified
 * </ul>
 *
 * <p>In general, specify {@code proxyTargetClass} to enforce a CGLIB proxy,
 * or specify one or more interfaces to use a JDK dynamic proxy.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 12.03.2004
 * @see AdvisedSupport#setOptimize
 * @see AdvisedSupport#setProxyTargetClass
 * @see AdvisedSupport#setInterfaces
 */
@SuppressWarnings("serial")
public class DefaultAopProxyFactory implements AopProxyFactory, Serializable {

	@Override
	public AopProxy createAopProxy(AdvisedSupport config) throws AopConfigException {
		/**
		 * 影响aop 代理方式的因素
		 * 1.optimize:
		 * 		用来控制通过cglib创建的代理是否使用激进的优化策略 除非完全了解aop代理如何处理优化 否则不推荐用户使用这个设置
		 * 		目前这个属性仅用于cglib代理 对jdk代理无效
		 * 2.proxyTargetClass:
		 * 		这个属性为true时 目标类本身被代理而不是目标类的接口, 如果为true cglib代理将被创建 设置方式:
		 * 		<aop:aspectj-autoproxy proxy-target-class="true" />
		 * 3.hasNoUserSuppliedProxyInstance: 是否存在代理接口
		 *
		 * 即: 如果目标对象实现了接口 默认情况会采用jdk
		 * 	   如果目标对象实现了接口 可以强制使用cglib实现aop
		 * 	   如果目标没有实现了接口 必须采用cglib  spring 会自动在jdk 和 cglib直接转换
		 *
		 * 	强制采用cglib:
		 * 		1.添加cglib库
		 * 		2.配置	<aop:aspectj-autoproxy proxy-target-class="true" />
		 *
		 * 	代理的区别:
		 * 		jdk动态代理 只能对实现了接口的类生成代理 而不能针对类
		 * 		cglib 是针对类实现代理 主要是对指定的类生成一个子类 覆盖其中的方法 因为是继承 所以该类或方法最好不要声明成final
		 */
		if (config.isOptimize() || config.isProxyTargetClass() || hasNoUserSuppliedProxyInterfaces(config)) {
			Class<?> targetClass = config.getTargetClass();
			if (targetClass == null) {
				throw new AopConfigException("TargetSource cannot determine target class: " +
						"Either an interface or a target is required for proxy creation.");
			}
			/**
			 * 这里进行判断  如果是接口
			 * 或者
			 * 当且仅当指定的类使用getProxyClass方法或newProxyInstance方法动态生成为代理类时
			 * 走jdk动态代理
			 */
			if (targetClass.isInterface() || Proxy.isProxyClass(targetClass)) {
				return new JdkDynamicAopProxy(config);
			}
			// 使用cglib进行代理
			return new ObjenesisCglibAopProxy(config);
		}
		else {
			return new JdkDynamicAopProxy(config);
		}
	}

	/**
	 * Determine whether the supplied {@link AdvisedSupport} has only the
	 * {@link org.springframework.aop.SpringProxy} interface specified
	 * (or no proxy interfaces specified at all).
	 */
	private boolean hasNoUserSuppliedProxyInterfaces(AdvisedSupport config) {
		Class<?>[] ifcs = config.getProxiedInterfaces();
		return (ifcs.length == 0 || (ifcs.length == 1 && SpringProxy.class.isAssignableFrom(ifcs[0])));
	}

}
