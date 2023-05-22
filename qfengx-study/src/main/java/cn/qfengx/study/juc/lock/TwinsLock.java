package cn.qfengx.study.juc.lock;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 带限的共享式占用锁
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-04-26 22:56
 */
public class TwinsLock implements Lock {

	private final Sync sync = new Sync(2);

	private static final class Sync extends AbstractQueuedSynchronizer {

		private static final long serialVersionUID = -4061531803231908102L;

		Sync(int count) {
			if (count <= 0) {
				throw new IllegalArgumentException("sync count must large than zero.");
			}
			setState(count);
		}

		@Override
		protected int tryAcquireShared(int arg) {
			for (;;) {
				int current = getState();
				int newCount = current - arg;
				if (newCount < 0 || compareAndSetState(current, newCount)) {
					return newCount;
				}
			}
		}

		@Override
		protected boolean tryReleaseShared(int arg) {
			for (;;) {
				int current = getState();
				int newCount = current + arg;
				if (compareAndSetState(current, newCount)) {
					return true;
				}
			}
		}
	}

	@Override
	public void lock() {
		sync.acquireShared(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {

	}

	@Override
	public boolean tryLock() {
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return false;
	}

	@Override
	public void unlock() {
		sync.releaseShared(1);
	}

	@Override
	public Condition newCondition() {
		return null;
	}

}
