package com.mfy.lock.component;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author maofangyun
 * @date 2021/9/6 17:20
 */
@Component
@ConditionalOnProperty(value = "zookeeper.address")
public class ZooKeeperLock implements Lock, InitializingBean {

    @Autowired
    private Environment environment;

    private CuratorFramework client;

    private InterProcessMutex lock;

    @Override
    public String getName() {
        return "zookeeperLock";
    }

    @Override
    public void lock(long leaseTime, TimeUnit unit) throws Exception{
        lock = new InterProcessMutex(client,"/lock");
        lock.acquire(leaseTime, unit);
    }

    @Override
    public void unlock() throws Exception{
        if(lock.isAcquiredInThisProcess() && lock.isOwnedByCurrentThread()){
            lock.release();
        }
    }

    @Override
    public void afterPropertiesSet() {
        client = CuratorFrameworkFactory.newClient(
            environment.getProperty("zookeeper.address"),
            Integer.valueOf(environment.getProperty("zookeeper.sessionTimeoutMs")),
            Integer.valueOf(environment.getProperty("zookeeper.connectionTimeoutMs")),
            new RetryNTimes(Integer.valueOf(environment.getProperty("zookeeper.retryCount")),
                    Integer.valueOf(environment.getProperty("zookeeper.elapsedTimeMs"))));
    }
}
