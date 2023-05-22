package cn.qfengx.study.juc.lock;

import cn.qfengx.study.juc.thread.pool.DefaultThreadPool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

/**
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-04-26 16:39
 */
public class CustFairSyncLockTest {

	private int sum = 0;

	public void add() {
		sum++;
	}

	public int get() {
		return sum;
	}

	public static void main(String[] args) throws InterruptedException {
		for (int a = 0; a < 10; a++) {
			CustFairSyncLockTest custFairSyncLock = new CustFairSyncLockTest();
			int num = 10000;
			CountDownLatch count = new CountDownLatch(num);
			CustFairSyncLock custLock = new CustFairSyncLock();
			for (int i = 0; i < num; i++) {
				new Thread(() -> {
					custLock.lock();
					try {
						custFairSyncLock.add();
						count.countDown();
					} finally {
						custLock.unlock();
					}
				}).start();
			}
			count.await();
			System.out.println(custFairSyncLock.get());
		}

//		Executors.newCachedThreadPool().execute("name" + );
//		Executors.newCachedThreadPool().execute("chuangjianh" + appid, runnable);
		System.out.println("=============================");
		DefaultThreadPool pool = new DefaultThreadPool(5);
		for (int a = 0; a < 10; a++) {
			CustFairSyncLockTest custFairSyncLock = new CustFairSyncLockTest();
			int num = 10000;
			CountDownLatch count = new CountDownLatch(num);
			CustFairSyncLock custLock = new CustFairSyncLock();
			for (int i = 0; i < num; i++) {
				pool.execute(() -> {
					custLock.lock();
					try {
						custFairSyncLock.add();
						count.countDown();
					} finally {
						custLock.unlock();
					}
				});
			}
			count.await();
			System.out.println(custFairSyncLock.get());
			pool.shutdown();
		}
	}


}
