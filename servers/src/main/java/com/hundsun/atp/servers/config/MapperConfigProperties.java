package com.hundsun.atp.servers.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("mybatis-plus")
@Component
@Data
public class MapperConfigProperties {

    private String typeAliasesPackage;

    private String mapperLocation;

    private String configLocation;

}