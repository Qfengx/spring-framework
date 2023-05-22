package cn.qfengx.study.juc;

import com.alibaba.fastjson.JSON;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-04-24 16:49
 */
public class JMXTest {

	static class Demo {
		private int age;
		private transient String name;

		public Demo(int age, String name) {
			this.age = age;
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public static void main(String[] args) {
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
		for (ThreadInfo threadInfo : threadInfos) {
			System.out.println(threadInfo.getThreadId() + "----" + threadInfo.getThreadName());
		}
		Demo demo = new Demo(20, "4324");
		System.out.println(JSON.toJSONString(demo));
	}

}
