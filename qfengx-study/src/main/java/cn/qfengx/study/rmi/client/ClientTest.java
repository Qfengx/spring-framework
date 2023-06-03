package cn.qfengx.study.rmi.client;

import cn.qfengx.study.rmi.HelloRMIService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-05-29 00:44
 */
public class ClientTest {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("rmi-client.xml");
		HelloRMIService client = applicationContext.getBean("myClient", HelloRMIService.class);
		System.out.println(client.getAdd(1, 7));
	}

}
