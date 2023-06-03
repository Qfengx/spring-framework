package cn.qfengx.study.rmi.httpinvoker;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-05-29 01:48
 */
public class Client {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("rmi-invoker-client.xml");
        HttpInvokerTestI httpInvokerTestI = applicationContext.getBean("remoteService", HttpInvokerTestI.class);
        System.out.println(httpInvokerTestI.getTestPo("sadsadad"));
    }

}
