package cn.qfengx.study.rmi.httpinvoker;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-05-29 01:47
 */
public class Server {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("rmi-invoker-server.xml");
    }

}
