package cn.qfengx.study.spring.customtag;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-05-22 01:21
 */
public class UserBeanDefinitonParser extends AbstractSingleBeanDefinitionParser {

	@Override
	protected Class getBeanClass(Element element) {
		return User.class;
	}

	@Override
	protected void doParse(Element element, BeanDefinitionBuilder bean) {
		// 解析出需要的属性
		String userName = element.getAttribute("userName");
		String email = element.getAttribute("email");
		// 将提取的数据放入到BeanDefinitionBuilder中 待到完成所有bean的解析后统一注册到beanFactory中
		if (StringUtils.hasText(userName)) {
			bean.addPropertyValue("userName", userName);
		}
		if (StringUtils.hasText(email)) {
			bean.addPropertyValue("email", email);
		}
	}

}
