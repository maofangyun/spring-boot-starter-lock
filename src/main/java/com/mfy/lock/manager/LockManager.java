package com.mfy.lock.manager;

import com.mfy.lock.component.Lock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author maofangyun
 * @date 2021/9/6 14:36
 */
@Component
public class LockManager {

    @Autowired
    private ApplicationContext applicationContext;

    private static Map<String,Lock> LOCK_MAP = new HashMap<>();

    @PostConstruct
    public void init(){
        Map<String, Lock> beans = applicationContext.getBeansOfType(Lock.class);
        beans.entrySet().stream().forEach(item -> LOCK_MAP.putIfAbsent(item.getValue().getName(), item.getValue()));
    }

    public static Lock getLock(String lockName){
        return LOCK_MAP.get(lockName);
    }

}
