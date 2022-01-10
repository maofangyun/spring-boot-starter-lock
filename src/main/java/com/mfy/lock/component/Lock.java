package com.mfy.lock.component;

import java.util.concurrent.TimeUnit;

/**
 * @author maofangyun
 * @date 2021/9/6 14:42
 */
public interface Lock {

    String KEY = "springboot:lock";

    String getName();

    void lock(long leaseTime, TimeUnit unit) throws Exception;

    void unlock() throws Exception;
}
