package com.mfy.lock.advisor;

import java.lang.annotation.*;

/**
 * @author maofangyun
 * @date 2021/9/6 14:55
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Lock {

    String name() default "redisLock";

    int expire() default 60;

    String key() default "lock";

}
