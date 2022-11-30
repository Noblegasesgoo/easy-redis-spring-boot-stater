package com.noblegasesgoo.platform.utils.redis;

import cn.hutool.core.collection.IterUtil;
import org.redisson.api.*;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author: noblegasesgoo
 * @CreateDate: 2022年11月30日 15:07
 * @ProjectName: easy-redis-spring-boot-starter
 * @version: 0.0.1
 * @FileName: RedissonUtil
 * @Description: Redisson 工具类
 */

@Component
public class RedissonUtil {

    private RedissonClient redissonClient;

    public RedissonUtil(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public long rateLimiter(String key, RateType rateType, int rate, int rateInterval) {
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        rateLimiter.trySetRate(rateType, (long)rate, (long)rateInterval, RateIntervalUnit.SECONDS);
        return rateLimiter.tryAcquire() ? rateLimiter.availablePermits() : -1L;
    }

    public RedissonClient getClient() {
        return redissonClient;
    }

    public <T> void publish(String channelKey, T msg, Consumer<T> consumer) {
        RTopic topic = redissonClient.getTopic(channelKey);
        topic.publish(msg);
        consumer.accept(msg);
    }

    public <T> void publish(String channelKey, T msg) {
        RTopic topic = redissonClient.getTopic(channelKey);
        topic.publish(msg);
    }

    public <T> void subscribe(String channelKey, Class<T> clazz, Consumer<T> consumer) {
        RTopic topic = redissonClient.getTopic(channelKey);
        topic.addListener(clazz, (channel, msg) -> {
            consumer.accept(msg);
        });
    }

    public <T> void setCacheObject(String key, T value) {
        setCacheObject(key, value, false);
    }

    public <T> void setCacheObject(String key, T value, boolean isSaveTtl) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        if (isSaveTtl) {
            try {
                bucket.setAndKeepTTL(value);
            } catch (Exception e) {
                long timeToLive = bucket.remainTimeToLive();
                bucket.set(value);
                bucket.expire(Duration.ofMillis(timeToLive));
            }
        } else {
            bucket.set(value);
        }

    }

    public <T> void setCacheObject(String key, T value, Duration duration) {
        RBucket<T> result = redissonClient.getBucket(key);
        result.set(value);
        result.expire(duration);
    }

    public <T> void addObjectListener(String key, ObjectListener listener) {
        RBucket<T> result = redissonClient.getBucket(key);
        result.addListener(listener);
    }

    public boolean expire(String key, long timeout) {
        return expire(key, Duration.ofSeconds(timeout));
    }

    public boolean expire(String key, Duration duration) {
        RBucket rBucket = redissonClient.getBucket(key);
        return rBucket.expire(duration);
    }

    public <T> T getCacheObject(String key) {
        RBucket<T> rBucket = redissonClient.getBucket(key);
        return rBucket.get();
    }

    public <T> long getTimeToLive(String key) {
        RBucket<T> rBucket = redissonClient.getBucket(key);
        return rBucket.remainTimeToLive();
    }

    public boolean deleteObject(String key) {
        return redissonClient.getBucket(key).delete();
    }

    public void deleteObject(Collection collection) {
        RBatch batch = redissonClient.createBatch();
        collection.forEach((t) -> {
            batch.getBucket(t.toString()).deleteAsync();
        });
        batch.execute();
    }

    public <T> boolean setCacheList(String key, List<T> dataList) {
        RList<T> rList = redissonClient.getList(key);
        return rList.addAll(dataList);
    }

    public <T> void addListListener(String key, ObjectListener listener) {
        RList<T> rList = redissonClient.getList(key);
        rList.addListener(listener);
    }

    public <T> List<T> getCacheList(String key) {
        RList<T> rList = redissonClient.getList(key);
        return rList.readAll();
    }

    public <T> boolean setCacheSet(String key, Set<T> dataSet) {
        RSet<T> rSet = redissonClient.getSet(key);
        return rSet.addAll(dataSet);
    }

    public <T> void addSetListener(String key, ObjectListener listener) {
        RSet<T> rSet = redissonClient.getSet(key);
        rSet.addListener(listener);
    }

    public <T> Set<T> getCacheSet(String key) {
        RSet<T> rSet = redissonClient.getSet(key);
        return rSet.readAll();
    }

    public <T> void setCacheMap(String key, Map<String, T> dataMap) {
        if (dataMap != null) {
            RMap<String, T> rMap = redissonClient.getMap(key);
            rMap.putAll(dataMap);
        }

    }

    public <T> void addMapListener(String key, ObjectListener listener) {
        RMap<String, T> rMap = redissonClient.getMap(key);
        rMap.addListener(listener);
    }

    public <T> Map<String, T> getCacheMap(String key) {
        RMap<String, T> rMap = redissonClient.getMap(key);
        return rMap.getAll(rMap.keySet());
    }

    public <T> void setCacheMapValue(String key, String hKey, T value) {
        RMap<String, T> rMap = redissonClient.getMap(key);
        rMap.put(hKey, value);
    }

    public <T> T getCacheMapValue(String key, String hKey) {
        RMap<String, T> rMap = redissonClient.getMap(key);
        return rMap.get(hKey);
    }

    public <T> T delCacheMapValue(String key, String hKey) {
        RMap<String, T> rMap = redissonClient.getMap(key);
        return rMap.remove(hKey);
    }

    public <K, V> Map<K, V> getMultiCacheMapValue(String key, Set<K> hKeys) {
        RMap<K, V> rMap = redissonClient.getMap(key);
        return rMap.getAll(hKeys);
    }

    public void setAtomicValue(String key, long value) {
        RAtomicLong atomic = redissonClient.getAtomicLong(key);
        atomic.set(value);
    }

    public long getAtomicValue(String key) {
        RAtomicLong atomic = redissonClient.getAtomicLong(key);
        return atomic.get();
    }

    public long incrAtomicValue(String key) {
        RAtomicLong atomic = redissonClient.getAtomicLong(key);
        return atomic.incrementAndGet();
    }

    public long decrAtomicValue(String key) {
        RAtomicLong atomic = redissonClient.getAtomicLong(key);
        return atomic.decrementAndGet();
    }

    public Collection<String> keys(String pattern) {
        Iterable<String> iterable = redissonClient.getKeys().getKeysByPattern(pattern);
        return IterUtil.toList(iterable);
    }

    public Boolean hasKey(String key) {
        RKeys rKeys = redissonClient.getKeys();
        return rKeys.countExists(new String[]{key}) > 0L;
    }
}
