package cn.qfengx.study.mq.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

/**
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-05-08 13:17
 */
public class RabbitMqUtils {


	public static Channel getChannel() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setPort(5672);
		factory.setUsername("root");
		factory.setPassword("root");
		Connection connection = factory.newConnection();
		Optional<Channel> channel = connection.openChannel();
		return channel.get();
	}


}
