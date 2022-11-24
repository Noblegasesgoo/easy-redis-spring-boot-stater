package com.noblegasesgoo.platform.service.redisson.impl;

import com.noblegasesgoo.platform.service.redisson.IRedissonDistributedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author: noblegasesgoo
 * @CreateDate: 2022年11月23日 15:04
 * @ProjectName: easy-redis-spring-boot-starter
 * @version: 0.0.1
 * @FileName: RedissonDistributedLock
 * @Description:
 */

@Component
public class RedissonDistributedLock implements IRedissonDistributedLock {

    private static final Logger log = LoggerFactory.getLogger(RedissonDistributedLock.class);

    private RedissonClient redissonClient;

    public RedissonDistributedLock(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public void lock(String key) {
        RLock lock = this.redissonClient.getLock(key);
        lock.lock();
    }

    @Override
    public void unlock(String key) {
        RLock lock = this.redissonClient.getLock(key);
        if (lock.isHeldByCurrentThread()) {
            try {
                lock.unlock();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    @Override
    public void lock(String key, Long leaseTime) {
        RLock lock = this.redissonClient.getLock(key);
        lock.lock(leaseTime, TimeUnit.SECONDS);
    }

    @Override
    public void lock(String key, TimeUnit unit, Long leaseTime) {
        RLock lock = this.redissonClient.getLock(key);
        lock.lock(leaseTime, unit);
    }

    @Override
    public boolean tryLock(String key) {
        RLock lock = this.redissonClient.getLock(key);
        return lock.tryLock();
    }

    @Override
    public boolean tryLock(String key, TimeUnit unit, Long waitTime, Long leaseTime) throws InterruptedException {
        RLock lock = this.redissonClient.getLock(key);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException var9) {
            return false;
        }
    }

    @Override
    public boolean isLocked(String key) {
        RLock lock = this.redissonClient.getLock(key);
        return lock.isLocked();
    }

}
