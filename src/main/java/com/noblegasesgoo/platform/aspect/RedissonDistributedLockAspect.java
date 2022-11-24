package com.noblegasesgoo.platform.aspect;

import com.noblegasesgoo.platform.annotation.redisson.DistributedLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author: noblegasesgoo
 * @CreateDate: 2022年11月23日 16:49
 * @ProjectName: easy-redis-spring-boot-starter
 * @version: 0.0.1
 * @FileName: RedissonDistributedLockAspect
 * @Description:
 */

@Aspect
@Component
public class RedissonDistributedLockAspect {

    private static final Logger log = LoggerFactory.getLogger(RedissonDistributedLockAspect.class);

    @Resource
    private RedissonClient redissonClient;

    public RedissonDistributedLockAspect() {
    }

    @Around("@annotation(com.noblegasesgoo.platform.annotation.redisson.DistributedLock)")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 获取被标注方法
        Method method = methodSignature.getMethod();
        // 获取被标注方法上的注解DistributedLock
        DistributedLock distributedLock = (DistributedLock) method.getAnnotation(DistributedLock.class);

        // 拿到当前用来上分布式锁的key
        String key = (String) AnnotationResolver.newInstance().resolver(joinPoint, distributedLock.RedissonLock());
        // 获取锁获取失败的提示
        String failGetLockTip = (String) AnnotationResolver.newInstance().resolver(joinPoint, distributedLock.tip());
        // 获取锁持有时间
        long leaseTime = distributedLock.leaseTime();
        // 获取等待获取锁的时间
        long waitTime = distributedLock.waitTime();
        // 重新按照规则组合锁key
        key = "LOCK:" + method.getName() + ":" + key;

        // 获得一把锁
        RLock lock = this.redissonClient.getLock(key);
        boolean locked = false;

        Object res;
        try {
            // 加锁是否成功
            locked = this.getLock(lock, key, leaseTime, waitTime);
            if (!locked) {
                log.info("已经有人占用了锁...");
                throw new InterruptedException(failGetLockTip);
            }

            log.info("获取到锁...,{}", key);
            res = joinPoint.proceed();
        } finally {

            try {
                if (locked) {
                    lock.unlock();
                }
            } catch (Exception e) {
                log.info("解锁失败{}，请好好检查！", key);
            }

        }

        return res;
    }

    private boolean getLock(RLock rLock, String lockKey, long leaseTime, long waitTime) {
        try {
            return rLock.tryLock(leaseTime, waitTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("Fail add lock,  key: [{}] ", lockKey, e);
            return false;
        }
    }
}
