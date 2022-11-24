package com.noblegasesgoo.platform.annotation.redisson;

import java.lang.annotation.*;

/**
 * @author: noblegasesgoo
 * @CreateDate: 2022年11月23日 15:58
 * @ProjectName: easy-redis-spring-boot-starter
 * @version: 0.0.1
 * @FileName: RedissonDistributedLock
 * @Description:
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DistributedLock {

    String RedissonLock() default "RedissonLock";

    long leaseTime() default 3L;

    long waitTime() default 3L;

    String tip() default "正在操作中";
}
