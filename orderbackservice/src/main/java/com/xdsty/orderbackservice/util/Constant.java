package com.xdsty.orderbackservice.util;


public final class Constant {

    /**
     * 公共配置命名空间
     */
    public static String COMMON_CENTER_GROUPID = "COMMON_CONF_GROUP";

    /**
     * 代付款订单保存到数据库中的前缀  order_back_{orderId}
     */
    public static final String ORDER_BACK_PREFIX = "order_back_";

    /**
     * redis分布式锁前缀
     */
    public static final String REDIS_LOCK_PREFIX = "redis_lock_prefix";

    /**
     * 订单回滚topic
     */
    public static final String ORDER_ROLLBACK_TOPIC = "order-rollback";
}
