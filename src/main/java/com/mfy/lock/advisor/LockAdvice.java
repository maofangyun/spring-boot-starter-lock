package com.mfy.lock.advisor;

import com.mfy.lock.LockProperties;
import com.mfy.lock.component.Lock;
import com.mfy.lock.manager.LockManager;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodClassKey;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author maofangyun
 * @date 2021/9/6 14:45
 */
public class LockAdvice implements MethodInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Lock lock = null;
        Class<?> declaringClass = methodInvocation.getMethod().getDeclaringClass();
        Method method = AopUtils.getMostSpecificMethod(methodInvocation.getMethod(),declaringClass);
        MethodClassKey cacheKey = LockPointCut.getCacheKey(method, declaringClass);
        LockProperties lockProperties = LockPointCut.PROPERTIES_MAP.get(cacheKey);
        lock = LockManager.getLock(lockProperties.getName());
        if(!redisTemplate.hasKey(lockProperties.getKey())){
            try {
                lock.lock(lockProperties.getExpire(),TimeUnit.SECONDS);
            } finally {
                if(lock != null){
                    lock.unlock();
                }
            }
        }
        return methodInvocation.proceed();
    }
}
