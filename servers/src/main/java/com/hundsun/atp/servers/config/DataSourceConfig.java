package com.hundsun.atp.servers.config;

import com.hundsun.atp.common.constant.GlobalConstants;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 数据源配置类
 * @author: weishihuai
 */
@Configuration
@MapperScan(basePackages = "com.hundsun.atp.persister.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig {

    /**
     * 数据源1
     *
     * @Primary: 标志这个 Bean 如果在多个同类 Bean 候选时，该 Bean 优先被考虑。
     * 多数据源配置的时候注意，必须要有一个主数据源，用 @Primary 标志该 Bean
     */
    @Primary
    @Bean
    public DataSource datasource01() {
        return getDataSource1();
    }

    /**
     * 数据源2
     */
    @Bean
    public DataSource datasource02() {
        return getDataSource2();
    }

    /**
     * 数据源3
     */
    @Bean
    public DataSource datasource03() {
        return getDataSource3();
    }

    /**
     * 多数据源需要自己设置sqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(MapperConfigProperties mapperConfigProperties) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(routingDataSource());
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 实体类对应的位置
        bean.setTypeAliasesPackage("com.hundsun.atp.persister.model");
        // mybatis的XML的配置
        bean.setMapperLocations(resolver.getResources("classpath:mapper/**/*.xml"));
        bean.setConfigLocation(resolver.getResource("classpath:mybatis-conf.xml"));
        return bean.getObject();
    }

    /**
     * 设置事务，事务需要知道当前使用的是哪个数据源才能进行事务处理
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(routingDataSource());
    }

    /**
     * 设置数据源路由，通过DataSourceRouterConfig类中的determineCurrentLookupKey()方法返回值决定使用哪个数据源
     */
    @Bean
    public AbstractRoutingDataSource routingDataSource() {
        DataSourceRouterConfig dynamicDataSource = new DataSourceRouterConfig();
        Map<Object, Object> targetDataSources = new HashMap<>(16);
        targetDataSources.put(GlobalConstants.DATA_SOURCE_DATASOURCE01, datasource01());
        targetDataSources.put(GlobalConstants.DATA_SOURCE_DATASOURCE02, datasource02());
        targetDataSources.put(GlobalConstants.DATA_SOURCE_DATASOURCE03, datasource03());
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(datasource01());
        return dynamicDataSource;
    }

    @Bean("datasource112")
    @ConfigurationProperties("spring.datasource.mysql.datasource112.hikari")
    public DataSource getDataSource1() {
        DataSource datasource = getDataSourceProperties1()
                .initializeDataSourceBuilder()
                .build();
        return datasource;
    }

    @Bean("datasource113")
    @ConfigurationProperties("spring.datasource.mysql.datasource113.hikari")
    public DataSource getDataSource2() {
        DataSource datasource = getDataSourceProperties2()
                .initializeDataSourceBuilder()
                .build();
        return datasource;
    }

    @Bean("datasource114")
    @ConfigurationProperties("spring.datasource.mysql.datasource114.hikari")
    public DataSource getDataSource3() {
        DataSource datasource = getDataSourceProperties3()
                .initializeDataSourceBuilder()
                .build();
        return datasource;
    }

    @Bean
    @ConfigurationProperties("spring.datasource.mysql.datasource112")
    public DataSourceProperties getDataSourceProperties1() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.mysql.datasource113")
    public DataSourceProperties getDataSourceProperties2() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.mysql.datasource114")
    public DataSourceProperties getDataSourceProperties3() {
        return new DataSourceProperties();
    }


}
