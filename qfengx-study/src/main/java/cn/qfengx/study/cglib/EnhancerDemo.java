package cn.qfengx.study.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-05-25 19:35
 */
public class EnhancerDemo {

	private static class MethodInterceptorImpl implements MethodInterceptor {

		@Override
		public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
			System.out.println("before invoke");
			Object result = proxy.invokeSuper(obj, args);
			System.out.println("after invoke");
			return result;
		}
	}

	public void test() {
		System.out.println("EnhancerDemo");
	}

	public static void main(String[] args) {
//		Enhancer enhancer = new Enhancer();
//		// 设置父类
//		enhancer.setSuperclass(EnhancerDemo.class);
//		enhancer.setCallback(new MethodInterceptorImpl());
//
//		EnhancerDemo enhancerDemo = (EnhancerDemo) enhancer.create();
//		enhancerDemo.test();
		EnhancerDemo enhancerDemo = new EnhancerDemo();
		enhancerDemo.test();
	}

}
