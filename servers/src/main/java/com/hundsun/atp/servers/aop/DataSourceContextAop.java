package com.hundsun.atp.servers.aop;

import com.hundsun.atp.common.annotation.DynamicSwitchDataSource;
import com.hundsun.atp.servers.config.DataSourceContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Description: 数据源切换AOP切面
 * @author: weishihuai
 * 说明：指定@Order(value = 1)保证切面优先级高于事务切面优先级[@EnableTransactionManagement(order = 10)].
 */
@Aspect
@Order(value = 1)
@Component
public class DataSourceContextAop {
 
 
    @Around("@annotation(com.hundsun.atp.common.annotation.DynamicSwitchDataSource)")
    public Object dynamicSwitchDataSource(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        boolean clear = false;
        try {
            MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
            Method method = signature.getMethod();
            DynamicSwitchDataSource dynamicSwitchDataSource = method.getAnnotation(DynamicSwitchDataSource.class);
            clear = dynamicSwitchDataSource.clear();
            //给DataSourceContext上下文赋值为当前数据源
            DataSourceContext.set(dynamicSwitchDataSource.value());
            System.out.println("当前切换数据源至：" + dynamicSwitchDataSource.value());
            return proceedingJoinPoint.proceed();
        } finally {
            if (clear) {
                DataSourceContext.clear();
            }
        }
    }
 
}