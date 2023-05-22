package cn.qfengx.study.juc.thread.pool;

import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.util.concurrent.CountDownLatch;

/**
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-04-26 00:36
 */
public class DefaultThreadPoolTest {

	@Test
	public void testThread() throws InterruptedException {
		System.out.println(ManagementFactory.getRuntimeMXBean().getName());
		DefaultThreadPool threadPool = new DefaultThreadPool();
		int num = 30;
		CountDownLatch count = new CountDownLatch(num);
		for (int i = 0; i < num; i++) {
			int finalI = i;
			threadPool.execute(() -> {
				System.out.println("exec => " + Thread.currentThread().getName() + ", num: " + finalI + ", pool task size => " + threadPool.getTaskSize());
				count.countDown();
			});
		}
		count.await();
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println(ManagementFactory.getRuntimeMXBean().getName());
		DefaultThreadPool threadPool = new DefaultThreadPool(3);
		int num = 5;
		CountDownLatch count = new CountDownLatch(num);
		for (int i = 0; i < num; i++) {
			int finalI = i;
			threadPool.execute(() -> {
				System.out.println("exec => " + Thread.currentThread().getName() + ", num: " + finalI + ", pool task size => " + threadPool.getTaskSize());
				count.countDown();
			});
		}
		count.await();
		// 关闭连接池, 因为非守护进程会影响jvm退出
		threadPool.shutdown();
		System.out.println("exec over");
	}

}
