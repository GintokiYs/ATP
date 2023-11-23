package com.hundsun.atp.servers.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
 
/**
 * @Description: 数据源路由的配置, 继承AbstractRoutingDataSource类，重写determineCurrentLookupKey()方法
 * @author: yeyh
 */
public class DataSourceRouterConfig extends AbstractRoutingDataSource {
 
    /**
     * 最终的determineCurrentLookupKey返回的是从DataSourceContext上下文中获取的,因此在动态切换数据源的时候(即AOP切面处理时)应该给DataSourceContext赋值
     */
    @Override
    protected Object determineCurrentLookupKey() {
        //从上下文环境中获取当前数据源信息
        return DataSourceContext.get();
    }
 
}