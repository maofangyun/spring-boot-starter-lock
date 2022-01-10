package com.mfy.lock.component;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author maofangyun
 * @date 2021/9/6 16:01
 */
@Component
@ConditionalOnProperty(value = "spring.redis.host")
public class RedisLock implements Lock, InitializingBean {

    @Autowired
    private Environment environment;

    private RedissonClient redissonClient;

    private RLock lock;

    @Override
    public String getName() {
        return "redisLock";
    }

    @Override
    public void lock(long leaseTime, TimeUnit unit) throws Exception{
        lock = redissonClient.getLock(KEY);
        lock.lock(leaseTime,unit);
    }

    @Override
    public void unlock() {
        if(lock.isLocked() && lock.isHeldByCurrentThread()){
            lock.unlock();
        }
    }

    @Override
    public void afterPropertiesSet() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + environment.getProperty("spring.redis.host") + ":" + environment.getProperty("spring.redis.port"));
        config.useSingleServer().setPassword(environment.getProperty("spring.redis.password"));
        config.useSingleServer().setConnectionPoolSize(Integer.valueOf(environment.getProperty("spring.redis.lettuce.pool.max-active")));
        config.useSingleServer().setConnectionMinimumIdleSize(Integer.valueOf(environment.getProperty("spring.redis.lettuce.pool.max-idle")));
        redissonClient = Redisson.create(config);
    }

}
