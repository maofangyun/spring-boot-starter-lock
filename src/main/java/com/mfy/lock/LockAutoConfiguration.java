package com.mfy.lock;

import com.mfy.lock.advisor.LockAdvice;
import com.mfy.lock.advisor.LockAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author maofangyun
 * @date 2021/9/6 14:35
 */
@Configuration
@ComponentScan("com.mfy.lock")
public class LockAutoConfiguration {

    @Bean
    public LockAdvisor lockAdvisor(LockAdvice lockAdvice){
        LockAdvisor lockAdvisor = new LockAdvisor();
        lockAdvisor.setAdvice(lockAdvice);
        return lockAdvisor;
    }

    @Bean
    public LockAdvice lockAdvice(){
        return new LockAdvice();
    }

}
