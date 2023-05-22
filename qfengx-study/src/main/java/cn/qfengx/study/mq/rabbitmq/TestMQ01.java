package cn.qfengx.study.mq.rabbitmq;

import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-05-08 13:16
 */
public class TestMQ01 {

	private static final String QUEUE_NAME = "T_Q";

	public static void main(String[] args) throws IOException, TimeoutException {
		Channel channel = RabbitMqUtils.getChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			String msg = sc.nextLine();
			channel.basicPublish("", "", null, msg.getBytes(StandardCharsets.UTF_8));
			System.out.println("send msg => " + msg);
		}
	}

}
