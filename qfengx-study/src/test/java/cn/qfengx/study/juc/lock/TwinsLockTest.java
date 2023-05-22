package cn.qfengx.study.juc.lock;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-04-26 23:02
 */
public class TwinsLockTest {

	class Worker extends Thread {

		private Lock lock = null;
		public Worker(Lock lock) {
			this.lock = lock;
		}

		@Override
		public void run() {
			while (true) {
				lock.lock();
				lock.lock();
				try {
					TimeUnit.SECONDS.sleep(1);
					System.out.println(Thread.currentThread().getName());
				} catch (Exception e) {
				} finally {
					lock.unlock();
				}
			}
		}
	}

	@Test
	public void test() throws InterruptedException {
		final Lock lock = new TwinsLock();
		for (int i = 0; i < 10; i++) {
			Worker w = new Worker(lock);
//			w.setDaemon(true);
			w.start();
		}
		for (int i = 0; i < 10; i++) {
			TimeUnit.SECONDS.sleep(3);
			System.out.println("sleep");
		}
	}

}
