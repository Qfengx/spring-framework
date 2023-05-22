package cn.qfengx.study.juc.thread.pool;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义线程池实现
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-04-26 00:16
 */
public class DefaultThreadPool<Task extends Runnable> {
	// 线程最大限制数
	private static final int MAX_WORKER_NUMBER = 10;
	// 线程池默认的数量
	private static final int DEFAULT_WORKER_NUMBERS = 5;
	// 线程池最小数量
	private static final int MIN_WORKER_NUMBERS = 1;
	// 工作列表
	private final LinkedList<Task> tasks = new LinkedList<>();
	// 工作者列表
	private final List<Worker> workers = Collections.synchronizedList(new ArrayList<>());

	// 工作者线程数量
	private int workerNum = DEFAULT_WORKER_NUMBERS;
	// 线程编号生成
	private AtomicInteger threadNum = new AtomicInteger();

	public DefaultThreadPool() {
		initWorkers(DEFAULT_WORKER_NUMBERS);
	}

	public DefaultThreadPool(int num) {
		workerNum = num > MAX_WORKER_NUMBER ? MAX_WORKER_NUMBER : num < MIN_WORKER_NUMBERS ?
				MIN_WORKER_NUMBERS : num;
		initWorkers(workerNum);
	}


	// 初始化线程工作者
	private void initWorkers(int num) {
		for (int i = 0; i < num; i++) {
			Worker worker = new Worker();
			workers.add(worker);
			Thread thread = new Thread(worker, "Cust TreadPool-Worker-" + threadNum.incrementAndGet());
			thread.start();
		}
		System.out.println("all worker thread start work => " + num);
	}


	public void execute(Task task) {
		if (Objects.nonNull(task)) {
			synchronized (tasks) {
				tasks.addLast(task);
				// 这里注意只要通知一个就可以了
				tasks.notify();
			}
//			System.out.println("add task");
		}
	}

	/**
	 * 遍历所有的工作者线程停止活动
	 */
	public void shutdown() {
//		if (workerNum > 1) {
//			removeWorker(workerNum - 1);
//		}
		for (Worker worker : workers) {
			worker.shutdown();
		}
//		// 这里要通知一次所有线程, 防止有休眠的
		synchronized (tasks) {
			tasks.notifyAll();
		}
	}

	public void addWorkers(int num) {
		synchronized (tasks) {
			// 限制新增的Worker数量不能超过最大值
			if (num + this.workerNum > MAX_WORKER_NUMBER) {
				num = MAX_WORKER_NUMBER - this.workerNum;
			}
			initWorkers(num);
			this.workerNum += num;
		}
//		System.out.println("add worker over: " + num);
	}

	public void removeWorker(int num) {
		synchronized (tasks) {
			if (num >= this.workerNum) {
				throw new IllegalArgumentException("beyond workNum");
			}
			// 按照给定的数量停止worker
			int count = 0;
			while (count <= num) {
				Worker worker = workers.get(0);
				if (workers.remove(worker)) {
					worker.shutdown();
					count++;
				}
			}
			this.workerNum -= count;
		}
	}

	public int getTaskSize() {
		return this.tasks.size();
	}

	class Worker implements Runnable {
		private volatile boolean running = true;

		@Override
		public void run() {
			while (running) {
//				System.out.println("execing => ");
				Task task = null;
				synchronized (tasks) {
					while (tasks.isEmpty() && this.running) {
						try {
							// map.put
							tasks.wait();
						} catch (InterruptedException e) {
							// 感知到外部对worker thread 的中断操作 返回
							Thread.currentThread().interrupt();
							return;
						}
					}
					if (this.running) {
						// 提取出一个任务
						task = tasks.removeFirst();
					}
				}
				if (Objects.nonNull(task)) {
					try {
						task.run();
					} catch (Exception e) {
						// job中的异常
						e.printStackTrace();
					}
				}
			}
//			System.out.println("worker is over ");
		}

		public void shutdown() {
			if (!this.running) {
				System.out.println("worker already to stop");
				return;
			}
			this.running = false;
//			System.out.println("worker to stop");
		}
	}

}
