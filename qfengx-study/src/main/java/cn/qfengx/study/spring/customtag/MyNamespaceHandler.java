package cn.qfengx.study.spring.customtag;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-05-22 01:27
 */
public class MyNamespaceHandler extends NamespaceHandlerSupport {
	@Override
	public void init() {
		registerBeanDefinitionParser("user", new UserBeanDefinitonParser());
	}
}
