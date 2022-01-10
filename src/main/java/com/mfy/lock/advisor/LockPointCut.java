package com.mfy.lock.advisor;

import com.mfy.lock.LockProperties;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.MethodClassKey;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author maofangyun
 * @date 2021/9/6 14:45
 */
public class LockPointCut extends StaticMethodMatcherPointcut {

    public static ConcurrentMap<MethodClassKey,LockProperties> PROPERTIES_MAP = new ConcurrentHashMap<>(32);

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        MethodClassKey cacheKey = getCacheKey(method, targetClass);
        if(PROPERTIES_MAP.containsKey(cacheKey)){
            return true;
        }
        // 避免桥接方法，获取原来的方法
        Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);
        Lock lock = specificMethod.getAnnotation(Lock.class);
        if(lock != null){
            LockProperties lockProperties = new LockProperties();
            lockProperties.setName(lock.name());
            lockProperties.setExpire(lock.expire());
            lockProperties.setKey(lock.key());
            PROPERTIES_MAP.putIfAbsent(cacheKey,lockProperties);
            return true;
        } else {
            return false;
        }
    }

    public static MethodClassKey getCacheKey(Method method, Class<?> targetClass) {
        return new MethodClassKey(method, targetClass);
    }

}
