package cn.com.glsx.stdinterface.modules.queue;

import com.glsx.plat.redis.utils.RedisUtils;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * redis延时队列存储
 *
 * @author xiangyanlin
 * @date 2022/9/9
 */
@Component
public class RedisDelayQueue {

    @Autowired
    private RedisUtils redisUtils;

    public boolean add(String taskKey, String businessNo, Long timestamp) {

        return redisUtils.zsSet(taskKey, businessNo, timestamp);
    }

    public Collection<String> getAndRemoveBusinessNo(String taskKey) {
        Set<String> result = Sets.newHashSet();
        System.out.println(System.currentTimeMillis());
        Set<String> values = redisUtils.zsRangeByScore(taskKey, 0, System.currentTimeMillis());
        if (values.isEmpty()) {
            return result;
        }
        for (String value : values) {
            if (Objects.isNull(value)) {
                continue;
            }
            result.add(value);
            redisUtils.zSetRemove(taskKey, value);
        }
        return result;
    }
}
