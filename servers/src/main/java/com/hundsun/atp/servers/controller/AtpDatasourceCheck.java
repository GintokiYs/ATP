package com.hundsun.atp.servers.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * 文件描述
 *
 * @ProductName: Hundsun HEP
 * @ProjectName: ATP
 * @Package: com.hundsun.atp.servers.controller
 * @Description: note
 * @Author: yeyh33975
 * @CreateDate: 2023-11-23 20:21
 * @UpdateUser: yeyh33975
 * @UpdateDate: 2023-11-23 20:21
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>
 * Copyright © 2023 Hundsun Technologies Inc. All Rights Reserved
 **/
@Controller
@Slf4j
public class AtpDatasourceCheck {
    @Resource
    private DataSource dataSource;

    @Autowired
    private DefaultDataSourceCreator dataSourceCreator;

    private static Map<String, DataSource> lostedDataSourceMap = new HashMap<>();

    @Scheduled(cron = "0/10 * * * * ?")
    private void checkDataSource() {
        log.info("开始检查datasource联通性");
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        for (Map.Entry<String, DataSource> entry : ds.getDataSources().entrySet()) {
            try {
                entry.getValue().getConnection().createStatement().execute("select 1 from dual");
                log.info("{} 还存活", entry.getKey());
            } catch (Exception e) {
                log.error("can't connect datasource, try to remove ", e);
                ds.removeDataSource(entry.getKey());
                lostedDataSourceMap.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Scheduled(cron = "0/15 * * * * ?")
    private void checkLostedDataSource() {
        log.info("开始检查失联datasource是否恢复正常");
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;

        if (CollectionUtil.isNotEmpty(lostedDataSourceMap)) {
            for (Map.Entry<String, DataSource> entry : lostedDataSourceMap.entrySet()) {
                try {
                    DataSourceProperty dataSourceProperty = new DataSourceProperty();
                    dataSourceProperty.setUrl("jdbc:mysql://10.20.30.113:33063/atp?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai");
                    dataSourceProperty.setDriverClassName("com.mysql.cj.jdbc.Driver");
                    dataSourceProperty.setUsername("root");
                    dataSourceProperty.setPassword("root");

                    DataSource newDatasource = dataSourceCreator.createDataSource(dataSourceProperty);
                    newDatasource.getConnection().createStatement().execute("select 1 from dual");
                    log.info("{} 转为活跃状态Datasource", entry.getKey());

                    ds.addDataSource(entry.getKey(), newDatasource);
                    lostedDataSourceMap.remove(entry.getKey());
                } catch (Exception e) {
                    log.error("{} 仍为非活跃状态Datasource ", entry.getKey(), e);
                }
            }
        }
    }
}
