package cn.qfengx.study.spring.bean.test01;

import cn.qfengx.study.spring.customtag.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-05-20 14:33
 */
public class BeanTest01 {

	public static void main(String[] args) {
		System.out.println(1);
		new BeanTest01().testSimpleLoad();
	}

//	@Test
	public void testSimpleLoad() {
		ClassPathResource classPathResource = new ClassPathResource("bean-test-01.xml");
		DefaultListableBeanFactory beanFactory = new XmlBeanFactory(classPathResource);
		beanFactory.setAllowCircularReferences(false);
		Object myTestBean = beanFactory.getBean("myTestBean");
		MyTestBean bean = beanFactory.getBean(MyTestBean.class);
		System.out.println(bean == myTestBean);
		System.out.println(bean.getTestStr());
		System.out.println(myTestBean.getClass().getName());

		User user = (User) beanFactory.getBean("tagUser");
		System.out.println(user);
	}
}