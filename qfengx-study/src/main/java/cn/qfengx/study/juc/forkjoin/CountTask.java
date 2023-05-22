package cn.qfengx.study.juc.forkjoin;

import java.util.concurrent.*;

/**
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-04-28 00:09
 */
public class CountTask extends RecursiveTask<Integer> {



	private static final int THRESHOLD = 2; // 判断是否还需要拆分的阈值
	private static final long serialVersionUID = -53609217974762351L;
	private int start;
	private int end;

	public CountTask(int start, int end) {
		this.start = start;
		this.end = end;
	}

	@Override
	protected Integer compute() {
		System.out.println("compute by thread => " + Thread.currentThread().getName());
		int sum = 0;
		boolean canCompute = (end - start) <= THRESHOLD;
		if (canCompute) {
			for (int i = start; i <= end; i++) {
				sum += i;
			}
		} else {
			// 这里说明需要继续拆分
			int middle = (start + end) / 2;
			CountTask leftTask = new CountTask(start, middle);
			CountTask rightTask = new CountTask(middle + 1, end);
			// 执行子任务 注意这里是将任务添加到当前线程的workQueue队列中
			leftTask.fork();
			rightTask.fork();
			// 等待子任务执行完成
			int leftResult = leftTask.join();
			int rightResult = rightTask.join();
			// 合并子任务
			sum = leftResult + rightResult;
		}
		return sum;
	}

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		// 生成一个计算任务, 负责计算1 + 2 + 3 + 4
		CountTask countTask = new CountTask(1, 40);
		Future<Integer> result = forkJoinPool.submit(countTask);
		System.out.println(result.get());
	}
}
