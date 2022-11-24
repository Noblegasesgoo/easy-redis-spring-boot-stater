package com.noblegasesgoo.platform.service.redisson;

import java.util.concurrent.TimeUnit;

/**
 * @author: noblegasesgoo
 * @CreateDate: 2022年11月23日 15:01
 * @ProjectName: easy-redis-spring-boot-starter
 * @version: 0.0.1
 * @FileName: IRedissonDistributedLock
 * @Description: Redisson 分布式锁能力接口
 */

public interface IRedissonDistributedLock {

    /**
     * 将当前key上分布式锁
     * @param key 作为锁的key
     */
    void lock(String key);

    /**
     * 将当前key所持有的分布式锁解锁
     * @param key 作为锁的key
     */
    void unlock(String key);

    /**
     * 将当前key与指定锁持有时长上分布式锁
     * @param key 作为锁的key
     * @param leaseTime 持有分布式锁的时长
     */
    void lock(String key, Long leaseTime);

    /**
     * 将当前key与指定时间单位的锁持有时长上分布式锁
     * @param key 作为锁的key
     * @param unit 时间单位
     * @param leaseTime 持有分布式锁的时长
     */
    void lock(String key, TimeUnit unit, Long leaseTime);

    /**
     * 尝试将当前传入的key上分布式锁
     * @param key 作为锁的key
     * @return 分布式锁是否成功上锁
     */
    boolean tryLock(String key);

    /**
     * 在获取锁的最长时间之间，尝试将当前key与指定时间单位的锁持有时长上分布式锁，
     * @param key 作为锁的key
     * @param unit 时间单位
     * @param waitTime 指定获取锁的最长时间
     * @param leaseTime 持有分布式锁的时长
     * @return
     * @throws InterruptedException
     */
    boolean tryLock(String key, TimeUnit unit, Long waitTime, Long leaseTime) throws InterruptedException;

    /**
     * 查看当前key是否被上了分布式锁
     * @param key 作为锁的key
     * @return 加分布式锁结果
     */
    boolean isLocked(String key);

}
