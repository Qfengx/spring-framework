package cn.qfengx.study.juc.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-04-28 16:45
 */
public class PoolTest {

	public static void main(String[] args) {
		ExecutorService pool = Executors
				.newFixedThreadPool(2);
		pool.execute(() -> {
			System.out.println(1);
		});
		pool.shutdown();
	}


}
