package cn.qfengx.study.juc;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-04-28 16:24
 */
public class ExchangerTest {

	private static final Exchanger<String> exgr = new Exchanger<String>();
	private static ExecutorService pool = Executors
			.newFixedThreadPool(2);

	public static void main(String[] args) {
		pool.execute(() -> {
			try {
				String A = "银行流水A";
				String res = exgr.exchange(A); // 会一直等待第二个线程exchange
				System.out.println("res: " + res);
			} catch (InterruptedException e) {}
		});
		pool.execute(() -> {
			try {
				String B = "银行流水B";
				String A = exgr.exchange(B);
				System.out.println("A and B: " + A.equals(B) + ", A is : " + A + ", B is : " + B);
			} catch (InterruptedException e) {}
		});
		pool.shutdown();
	}

}
