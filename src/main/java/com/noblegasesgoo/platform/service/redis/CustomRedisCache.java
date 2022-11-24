package com.noblegasesgoo.platform.service.redis;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author: noblegasesgoo
 * @CreateDate: 2022年11月23日 15:02
 * @ProjectName: easy-redis-spring-boot-starter
 * @version: 0.0.1
 * @FileName: CustomRedisCache
 * @Description:
 */
public class CustomRedisCache extends RedisCache {

    private RedisCacheWriter redisCacheWriter;
    private RedisCacheConfiguration configuration;
    String REGEX_STR = ".*\\#\\d+$";
    private static final String Splitter = "#";

    protected CustomRedisCache(String name, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig) {
        super(name, cacheWriter, cacheConfig);
        this.redisCacheWriter = cacheWriter;
        this.configuration = cacheConfig;
    }

    public void put(Object key, Object value) {
        String name = super.getName();
        if (Pattern.matches(this.REGEX_STR, name)) {
            List<String> keyList = Arrays.asList(name.split("#"));
            String finalName = (String)keyList.get(0);
            Long ttl = Long.valueOf((String)keyList.get(1));
            Object cacheValue = this.preProcessCacheValue(value);
            if (!this.isAllowNullValues() && cacheValue == null) {
                throw new IllegalArgumentException(String.format("Cache '%s' does not allow 'null' values. Avoid storing null via '@Cacheable(unless=\"#result == null\")' or configure RedisCache to allow 'null' via RedisCacheConfiguration.", name));
            }

            this.redisCacheWriter.put(finalName, this.serializeCacheKey(this.createCacheKey(key)), this.serializeCacheValue(cacheValue), Duration.ofSeconds(ttl));
        } else {
            super.put(key, value);
        }

    }

    protected String createCacheKey(Object key) {
        String convertedKey = this.convertKey(key);
        return !this.configuration.usePrefix() ? convertedKey : this.prefixCacheKey(convertedKey);
    }

    private String prefixCacheKey(String key) {
        String name = super.getName();
        if (Pattern.matches(this.REGEX_STR, name)) {
            List<String> keyList = Arrays.asList(name.split("#"));
            String finalName = (String)keyList.get(0);
            return this.configuration.getKeyPrefixFor(finalName) + key;
        } else {
            return this.configuration.getKeyPrefixFor(name) + key;
        }
    }
}
