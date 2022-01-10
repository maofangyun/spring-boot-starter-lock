package com.mfy.lock.component;

import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author maofangyun
 * @date 2021/9/6 17:20
 */
@Component
public class LocalLock implements Lock{

    private ReentrantLock lock = new ReentrantLock(false);

    @Override
    public String getName() {
        return "localLock";
    }

    @Override
    public void lock(long leaseTime, TimeUnit unit) throws Exception{
        lock.tryLock(leaseTime,unit);
    }

    @Override
    public void unlock() {
        if(lock.isLocked() && lock.isHeldByCurrentThread()){
            lock.unlock();
        }
    }
}
