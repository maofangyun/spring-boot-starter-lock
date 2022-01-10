package com.mfy.lock.advisor;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * @author maofangyun
 * @date 2021/9/6 14:44
 */
public class LockAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    private Pointcut pointcut = new LockPointCut();

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }
}
