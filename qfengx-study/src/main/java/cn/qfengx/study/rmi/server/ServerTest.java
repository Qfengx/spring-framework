package cn.qfengx.study.rmi.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-05-29 00:41
 */
public class ServerTest {

	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("rmi-bean.xml");

	}

}
