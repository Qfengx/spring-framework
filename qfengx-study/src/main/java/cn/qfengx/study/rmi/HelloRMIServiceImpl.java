package cn.qfengx.study.rmi;

/**
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-05-29 00:35
 */
public class HelloRMIServiceImpl implements HelloRMIService {
	@Override
	public int getAdd(int a, int b) {
		return a + b;
	}
}
