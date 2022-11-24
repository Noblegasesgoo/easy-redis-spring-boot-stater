package com.noblegasesgoo.platform.properties.redisson;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: noblegasesgoo
 * @CreateDate: 2022年11月23日 15:03
 * @ProjectName: easy-redis-spring-boot-starter
 * @version: 0.0.1
 * @FileName: DefaultRedissonProperties
 * @Description: Redisson 默认属性
 */

@Component
@ConfigurationProperties(prefix = "spring.redisson")
public class DefaultRedissonProperties {

    private String address;

    private Integer connectionMinimumIdleSize = 32;

    private int idleConnectionTimeout = 10000;

    private int pingTimeout = 1000;

    private int connectTimeout = 10000;

    private int timeout = 3000;

    private int retryAttempts = 3;

    private int retryInterval = 1500;

    private int reconnectionTimeout = 3000;

    private int failedAttempts = 3;

    private String password = null;

    private int subscriptionsPerConnection = 5;

    private String clientName = null;

    private int subscriptionConnectionMinimumIdleSize = 1;

    private int subscriptionConnectionPoolSize = 50;

    private int connectionPoolSize = 64;

    private int database = 0;

    private boolean dnsMonitoring = false;

    private int dnsMonitoringInterval = 5000;

    private String codec = "org.redisson.codec.JsonJacksonCodec";

    private int thread;

    private Integer nettyThreads;

    private Long lockWatchdogTimeout = 30000L;

    private Integer threads;

    private Boolean referenceEnabled = true;

    private Boolean keepPubSubOrder = true;

    private Boolean decodeInExecutor = false;

    private Boolean useScriptCache = false;

    private Integer minCleanUpDelay = 5;

    private Integer maxCleanUpDelay = 1800;

    private Boolean keepAlive = false;

    private Boolean tcpNoDelay = false;

    public DefaultRedissonProperties() {
    }

    public DefaultRedissonProperties(String address, Integer connectionMinimumIdleSize, int idleConnectionTimeout, int pingTimeout, int connectTimeout, int timeout, int retryAttempts, int retryInterval, int reconnectionTimeout, int failedAttempts, String password, int subscriptionsPerConnection, String clientName, int subscriptionConnectionMinimumIdleSize, int subscriptionConnectionPoolSize, int connectionPoolSize, int database, boolean dnsMonitoring, int dnsMonitoringInterval, String codec, int thread, Integer nettyThreads, Long lockWatchdogTimeout, Integer threads, Boolean referenceEnabled, Boolean keepPubSubOrder, Boolean decodeInExecutor, Boolean useScriptCache, Integer minCleanUpDelay, Integer maxCleanUpDelay, Boolean keepAlive, Boolean tcpNoDelay) {
        this.address = address;
        this.connectionMinimumIdleSize = connectionMinimumIdleSize;
        this.idleConnectionTimeout = idleConnectionTimeout;
        this.pingTimeout = pingTimeout;
        this.connectTimeout = connectTimeout;
        this.timeout = timeout;
        this.retryAttempts = retryAttempts;
        this.retryInterval = retryInterval;
        this.reconnectionTimeout = reconnectionTimeout;
        this.failedAttempts = failedAttempts;
        this.password = password;
        this.subscriptionsPerConnection = subscriptionsPerConnection;
        this.clientName = clientName;
        this.subscriptionConnectionMinimumIdleSize = subscriptionConnectionMinimumIdleSize;
        this.subscriptionConnectionPoolSize = subscriptionConnectionPoolSize;
        this.connectionPoolSize = connectionPoolSize;
        this.database = database;
        this.dnsMonitoring = dnsMonitoring;
        this.dnsMonitoringInterval = dnsMonitoringInterval;
        this.codec = codec;
        this.thread = thread;
        this.nettyThreads = nettyThreads;
        this.lockWatchdogTimeout = lockWatchdogTimeout;
        this.threads = threads;
        this.referenceEnabled = referenceEnabled;
        this.keepPubSubOrder = keepPubSubOrder;
        this.decodeInExecutor = decodeInExecutor;
        this.useScriptCache = useScriptCache;
        this.minCleanUpDelay = minCleanUpDelay;
        this.maxCleanUpDelay = maxCleanUpDelay;
        this.keepAlive = keepAlive;
        this.tcpNoDelay = tcpNoDelay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getConnectionMinimumIdleSize() {
        return connectionMinimumIdleSize;
    }

    public void setConnectionMinimumIdleSize(Integer connectionMinimumIdleSize) {
        this.connectionMinimumIdleSize = connectionMinimumIdleSize;
    }

    public int getIdleConnectionTimeout() {
        return idleConnectionTimeout;
    }

    public void setIdleConnectionTimeout(int idleConnectionTimeout) {
        this.idleConnectionTimeout = idleConnectionTimeout;
    }

    public int getPingTimeout() {
        return pingTimeout;
    }

    public void setPingTimeout(int pingTimeout) {
        this.pingTimeout = pingTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getRetryAttempts() {
        return retryAttempts;
    }

    public void setRetryAttempts(int retryAttempts) {
        this.retryAttempts = retryAttempts;
    }

    public int getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(int retryInterval) {
        this.retryInterval = retryInterval;
    }

    public int getReconnectionTimeout() {
        return reconnectionTimeout;
    }

    public void setReconnectionTimeout(int reconnectionTimeout) {
        this.reconnectionTimeout = reconnectionTimeout;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSubscriptionsPerConnection() {
        return subscriptionsPerConnection;
    }

    public void setSubscriptionsPerConnection(int subscriptionsPerConnection) {
        this.subscriptionsPerConnection = subscriptionsPerConnection;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getSubscriptionConnectionMinimumIdleSize() {
        return subscriptionConnectionMinimumIdleSize;
    }

    public void setSubscriptionConnectionMinimumIdleSize(int subscriptionConnectionMinimumIdleSize) {
        this.subscriptionConnectionMinimumIdleSize = subscriptionConnectionMinimumIdleSize;
    }

    public int getSubscriptionConnectionPoolSize() {
        return subscriptionConnectionPoolSize;
    }

    public void setSubscriptionConnectionPoolSize(int subscriptionConnectionPoolSize) {
        this.subscriptionConnectionPoolSize = subscriptionConnectionPoolSize;
    }

    public int getConnectionPoolSize() {
        return connectionPoolSize;
    }

    public void setConnectionPoolSize(int connectionPoolSize) {
        this.connectionPoolSize = connectionPoolSize;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public boolean isDnsMonitoring() {
        return dnsMonitoring;
    }

    public void setDnsMonitoring(boolean dnsMonitoring) {
        this.dnsMonitoring = dnsMonitoring;
    }

    public int getDnsMonitoringInterval() {
        return dnsMonitoringInterval;
    }

    public void setDnsMonitoringInterval(int dnsMonitoringInterval) {
        this.dnsMonitoringInterval = dnsMonitoringInterval;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public int getThread() {
        return thread;
    }

    public void setThread(int thread) {
        this.thread = thread;
    }

    public Integer getNettyThreads() {
        return nettyThreads;
    }

    public void setNettyThreads(Integer nettyThreads) {
        this.nettyThreads = nettyThreads;
    }

    public Long getLockWatchdogTimeout() {
        return lockWatchdogTimeout;
    }

    public void setLockWatchdogTimeout(Long lockWatchdogTimeout) {
        this.lockWatchdogTimeout = lockWatchdogTimeout;
    }

    public Integer getThreads() {
        return threads;
    }

    public void setThreads(Integer threads) {
        this.threads = threads;
    }

    public Boolean getReferenceEnabled() {
        return referenceEnabled;
    }

    public void setReferenceEnabled(Boolean referenceEnabled) {
        this.referenceEnabled = referenceEnabled;
    }

    public Boolean getKeepPubSubOrder() {
        return keepPubSubOrder;
    }

    public void setKeepPubSubOrder(Boolean keepPubSubOrder) {
        this.keepPubSubOrder = keepPubSubOrder;
    }

    public Boolean getDecodeInExecutor() {
        return decodeInExecutor;
    }

    public void setDecodeInExecutor(Boolean decodeInExecutor) {
        this.decodeInExecutor = decodeInExecutor;
    }

    public Boolean getUseScriptCache() {
        return useScriptCache;
    }

    public void setUseScriptCache(Boolean useScriptCache) {
        this.useScriptCache = useScriptCache;
    }

    public Integer getMinCleanUpDelay() {
        return minCleanUpDelay;
    }

    public void setMinCleanUpDelay(Integer minCleanUpDelay) {
        this.minCleanUpDelay = minCleanUpDelay;
    }

    public Integer getMaxCleanUpDelay() {
        return maxCleanUpDelay;
    }

    public void setMaxCleanUpDelay(Integer maxCleanUpDelay) {
        this.maxCleanUpDelay = maxCleanUpDelay;
    }

    public Boolean getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Boolean getTcpNoDelay() {
        return tcpNoDelay;
    }

    public void setTcpNoDelay(Boolean tcpNoDelay) {
        this.tcpNoDelay = tcpNoDelay;
    }
}
