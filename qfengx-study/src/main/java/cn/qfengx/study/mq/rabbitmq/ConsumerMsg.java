package cn.qfengx.study.mq.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-05-08 13:49
 */
public class ConsumerMsg {
	private static final String QUEUE_NAME = "T_Q";
	public static void main(String[] args) throws IOException, TimeoutException {

		Channel channel = RabbitMqUtils.getChannel();
		channel.basicConsume(QUEUE_NAME, new Consumer() {
			@Override
			public void handleConsumeOk(String consumerTag) {

			}

			@Override
			public void handleCancelOk(String consumerTag) {

			}

			@Override
			public void handleCancel(String consumerTag) throws IOException {
				System.out.println("tag => " + consumerTag);
			}

			@Override
			public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {

			}

			@Override
			public void handleRecoverOk(String consumerTag) {

			}

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

			}
		});
	}

}
