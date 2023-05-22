package cn.qfengx.study.juc.threadlocal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-05-06 11:34
 */
public class TransforTest {


	public static void main(String[] args) throws InterruptedException {
		CountDownLatch count = new CountDownLatch(10);
		ThreadLocal<String> threadLocal = new ThreadLocal<>();
		InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();
		inheritableThreadLocal.set("value => " + Thread.currentThread().getName());
		threadLocal.set("tname => " + Thread.currentThread().getName());
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		for (int i = 0; i < 5; i++) {
			executorService.execute(() -> {
				System.out.println(Thread.currentThread().getName() + " => get: " + threadLocal.get());
				System.out.println("child => " + inheritableThreadLocal.get());
				count.countDown();
			});
		}
		inheritableThreadLocal.set("value => update" + Thread.currentThread().getName());
		for (int i = 0; i < 10; i++) {
			executorService.execute(() -> {
				System.out.println(Thread.currentThread().getName() + " => get: " + threadLocal.get());
				System.out.println("child => " + inheritableThreadLocal.get());
				count.countDown();
			});
		}
		count.await();
		System.out.println(Thread.currentThread().getName() + " over => " + threadLocal.get());
	}

}
