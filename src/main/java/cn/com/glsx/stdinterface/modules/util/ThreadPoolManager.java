package cn.com.glsx.stdinterface.modules.util;

/**
 * @author xiangyanlin
 * @date 2022/4/14
 */
public class ThreadPoolManager {

    /**
     * 服务器线程数
     */
    private static final int N = Runtime.getRuntime().availableProcessors();

    /**
     * Io密集型任务线程数
     */
    private static final int NUM_IO_CORE = 2 * N;

    /**
     * 计算密集型任务
     */
    private final int NUM_COMPUTE_CORE = N + 1;

    public static int getIoTaskThreadNum() {
        return NUM_IO_CORE;
    }

}
