package cn.qfengx.study.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 通过信号量控制并发执行数
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-04-28 16:00
 */
public class SemaphoreTest {

	private static final int THREAD_COUNT = 30;
	private static final ExecutorService threadPool = Executors
			.newFixedThreadPool(THREAD_COUNT);

	private static Semaphore s = new Semaphore(10);

	public static void main(String[] args) {
		for (int i = 0; i < THREAD_COUNT; i++) {
			threadPool.execute(() -> {
				try {
					s.acquire(); // 获取一个信号令牌 如果
					System.out.println("save data");
				} catch (InterruptedException e) {} finally {
					s.release();
				}
			});
		}
		// shutdown
		threadPool.shutdown();
	}

}
