package cn.qfengx.study.juc.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自定义公平锁实现
 * 	这里锁的接口是 Lock, 但是内部实现原理 依托于内部类 AQS的子类
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-04-26 16:06
 */
public class CustFairSyncLock implements Lock {

	/**
	 * 即 state 0 表示 非锁定状态, 1 表示锁定状态
	 */
	private static class CustSyncLock extends AbstractQueuedSynchronizer {


		private static final long serialVersionUID = -1739090067652842425L;

		/**
		 * 是否处于占用状态
		 * @return
		 */
		@Override
		protected boolean isHeldExclusively() {
			return getState() == 1;
		}

		/**
		 * 状态为0的时候 获取锁
		 * @param arg the acquire argument. This value is always the one
		 *        passed to an acquire method, or is the value saved on entry
		 *        to a condition wait.  The value is otherwise uninterpreted
		 *        and can represent anything you like.
		 * @return
		 */
		@Override
		protected boolean tryAcquire(int arg) {
			// 只有状态为0的时候
			if (compareAndSetState(0, 1)) {
				// 设置当前独占的线程
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
			return false;
		}

		/**
		 * 释放锁, 将状态设置为0
		 * @param arg the release argument. This value is always the one
		 *        passed to a release method, or the current state value upon
		 *        entry to a condition wait.  The value is otherwise
		 *        uninterpreted and can represent anything you like.
		 * @return
		 */
		@Override
		protected boolean tryRelease(int arg) {
			// 这里必须要保证当前 线程是锁的持有者 如果不是则保持synchronized 语义 抛出异常
			if (getState() == 0) throw new IllegalMonitorStateException();
			setExclusiveOwnerThread(null);
			setState(0);
			return true;
		}

		Condition newCondition() {
			return new ConditionObject();
		}

	}

	private final CustSyncLock custSyncLock = new CustSyncLock();

	@Override
	public void lock() {
		custSyncLock.acquire(1);
	}

	@Override
	public boolean tryLock() {
		return custSyncLock.tryAcquire(1);
	}

	@Override
	public void unlock() {
		custSyncLock.release(1);
	}



	@Override
	public Condition newCondition() {
		return custSyncLock.newCondition();
	}

	/**
	 * 判断是否锁定状态
	 * @return
	 */
	public boolean isLocked() {
		return custSyncLock.isHeldExclusively();
	}

	public boolean hasQueuedThreads() {
		return custSyncLock.hasQueuedThreads();
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		custSyncLock.acquireInterruptibly(1);
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return custSyncLock.tryAcquireNanos(1, unit.toNanos(time));
	}
}
