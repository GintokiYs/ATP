package com.hundsun.atp.servers.config;

/**
 * @Description: 利用ThreadLocal封装的保存数据源上线的上下文
 * @author: yeyh
 */
public class DataSourceContext {
 
    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();
 
    /**
     * 设置当前数据源
     */
    public static void set(String datasourceType) {
        CONTEXT.set(datasourceType);
    }
 
    /**
     * 获取当前数据源
     */
    public static String get() {
        return CONTEXT.get();
    }
 
    /**
     * 清除当前数据源
     */
    public static void clear() {
        CONTEXT.remove();
    }
 
}
