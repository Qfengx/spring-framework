package cn.qfengx.study.rmi.httpinvoker;

/**
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-05-29 01:38
 */
public class HttpInvokerTestImpl implements HttpInvokerTestI {
    @Override
    public String getTestPo(String desp) {
        return "getTestPo " + desp;
    }
}
