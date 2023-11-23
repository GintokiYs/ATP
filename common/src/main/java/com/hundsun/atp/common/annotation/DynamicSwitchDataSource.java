package com.hundsun.atp.common.annotation;

import com.hundsun.atp.common.constant.GlobalConstants;

import java.lang.annotation.*;
 
/**
 * @Description: 自定义注解，主要用于标注在类上面，实现动态切换数据源
 * @author: yyh
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DynamicSwitchDataSource {
    /**
     * 默认数据源为datasource01
     */
    String value() default GlobalConstants.DATA_SOURCE_DATASOURCE01;
 
    /**
     * 清除
     */
    boolean clear() default true;
 
}