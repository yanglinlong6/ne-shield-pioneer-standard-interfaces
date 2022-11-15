package cn.com.glsx.stdinterface.modules.multithread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Service
public class AsyncTasks {

    /**
     * 异步填充设备激活经纬度和激活地址信息
     */
    @Async
    public void saveActiveInfo(List<String> snList, CountDownLatch countDownLatch) {

    }
}
