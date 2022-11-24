package com.noblegasesgoo.platform.service.redis;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

/**
 * @author: noblegasesgoo
 * @CreateDate: 2022年11月23日 15:01
 * @ProjectName: easy-redis-spring-boot-starter
 * @version: 0.0.1
 * @FileName: DefaultRedisCacheManager
 * @Description:
 */

public class DefaultRedisCacheManager extends RedisCacheManager {

    private final RedisCacheWriter cacheWriter;
    private final RedisCacheConfiguration defaultCacheConfig;

    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        return new CustomRedisCache(name, this.cacheWriter, cacheConfig != null ? cacheConfig : this.defaultCacheConfig);
    }

    public DefaultRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
        this.cacheWriter = cacheWriter;
        this.defaultCacheConfig = defaultCacheConfiguration;
    }
}
