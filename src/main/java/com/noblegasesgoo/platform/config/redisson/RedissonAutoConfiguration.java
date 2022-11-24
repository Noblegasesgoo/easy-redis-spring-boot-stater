package com.noblegasesgoo.platform.config.redisson;

import com.noblegasesgoo.platform.properties.redisson.DefaultRedissonProperties;
import com.noblegasesgoo.platform.service.redisson.IRedissonDistributedLock;
import com.noblegasesgoo.platform.service.redisson.impl.RedissonDistributedLock;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.config.TransportMode;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;

import javax.annotation.Resource;

/**
 * @author: noblegasesgoo
 * @CreateDate: 2022年11月23日 15:01
 * @ProjectName: easy-redis-spring-boot-starter
 * @version: 0.0.1
 * @FileName: RedissonAutoConfiguration
 * @Description: Redisson 自动配置类
 */

@Configuration
@ConditionalOnClass({Redisson.class, RedisOperations.class})
@AutoConfigureBefore({RedisAutoConfiguration.class})
@EnableConfigurationProperties({DefaultRedissonProperties.class, RedisProperties.class})
public class RedissonAutoConfiguration {

    @Resource
    private DefaultRedissonProperties redissonProperties;

    @Bean
    @ConditionalOnMissingBean({RedissonClient.class})
    public RedissonClient redissonClient() {
        Config config = new Config();

        try {
            config.setCodec((Codec)Class.forName(this.redissonProperties.getCodec()).newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        config.setTransportMode(TransportMode.NIO);
        if (this.redissonProperties.getThreads() != null) {
            config.setThreads(this.redissonProperties.getThreads());
        }

        if (this.redissonProperties.getNettyThreads() != null) {
            config.setNettyThreads(this.redissonProperties.getNettyThreads());
        }

        config.setReferenceEnabled(this.redissonProperties.getReferenceEnabled());
        config.setLockWatchdogTimeout(this.redissonProperties.getLockWatchdogTimeout());
        config.setKeepPubSubOrder(this.redissonProperties.getKeepPubSubOrder());
        config.setUseScriptCache(this.redissonProperties.getUseScriptCache());
        config.setMinCleanUpDelay(this.redissonProperties.getMinCleanUpDelay());
        config.setMaxCleanUpDelay(this.redissonProperties.getMaxCleanUpDelay());
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress(this.redissonProperties.getAddress());
        singleServerConfig.setSubscriptionConnectionMinimumIdleSize(this.redissonProperties.getSubscriptionConnectionMinimumIdleSize());
        singleServerConfig.setSubscriptionConnectionPoolSize(this.redissonProperties.getSubscriptionConnectionPoolSize());
        singleServerConfig.setConnectionMinimumIdleSize(this.redissonProperties.getConnectionMinimumIdleSize());
        singleServerConfig.setConnectionPoolSize(this.redissonProperties.getConnectionPoolSize());
        singleServerConfig.setIdleConnectionTimeout(this.redissonProperties.getIdleConnectionTimeout());
        singleServerConfig.setConnectTimeout(this.redissonProperties.getConnectTimeout());
        singleServerConfig.setTimeout(this.redissonProperties.getTimeout());
        singleServerConfig.setRetryAttempts(this.redissonProperties.getRetryAttempts());
        singleServerConfig.setRetryInterval(this.redissonProperties.getRetryInterval());
        singleServerConfig.setDatabase(this.redissonProperties.getDatabase());
        singleServerConfig.setPassword(this.redissonProperties.getPassword());
        singleServerConfig.setSubscriptionsPerConnection(this.redissonProperties.getSubscriptionsPerConnection());
        singleServerConfig.setKeepAlive(this.redissonProperties.getKeepAlive());
        singleServerConfig.setPingConnectionInterval(this.redissonProperties.getPingTimeout());
        singleServerConfig.setTcpNoDelay(this.redissonProperties.getTcpNoDelay());
        singleServerConfig.setTimeout(this.redissonProperties.getTimeout());
        return Redisson.create(config);
    }

    @Bean
    public IRedissonDistributedLock redissonDistributedLock(RedissonClient redissonClient) {
        return new RedissonDistributedLock(redissonClient);
    }

}
