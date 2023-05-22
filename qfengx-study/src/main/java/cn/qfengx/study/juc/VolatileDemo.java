package cn.qfengx.study.juc;

import java.util.concurrent.CountDownLatch;

/**
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-04-22 16:05
 */
public class VolatileDemo {

	private volatile Object object = new Object();

	private static volatile int num = 0;

	public static void main(String[] args) throws InterruptedException {
		int loopNum = 1000;
		CountDownLatch count = new CountDownLatch(loopNum);
		for (int i = 0; i < loopNum; i++) {
			new Thread(() -> {
				num++;
				count.countDown();
			}).start();
		}
		count.await();
		System.out.println(num);
	}

}
