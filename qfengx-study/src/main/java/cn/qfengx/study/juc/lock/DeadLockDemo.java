package cn.qfengx.study.juc.lock;

import java.util.concurrent.TimeUnit;

/**
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-04-22 14:26
 */
public class DeadLockDemo {

	private static String A = "A";
	private static String B = "B";

	public static void main(String[] args) {
		new DeadLockDemo().deadLock();
	}

	private void deadLock() {
		Thread t1 = new Thread(() -> {
			System.out.println("loading a");
			synchronized (A) {
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("loading B");
				synchronized (B) {
					System.out.println("1");
				}
			}
		});

		Thread t2 = new Thread(() -> {
			synchronized (B) {
				synchronized (A) {
					System.out.println("2");
				}
			}
		});

		t1.start();
		t2.start();
	}

}
