package cn.qfengx.study.spring.bean;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-05-23 09:27
 */
public class BeanContextTest {

	@Test
	public void testApplicationContext() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("bean-test-01.xml");

	}

}
