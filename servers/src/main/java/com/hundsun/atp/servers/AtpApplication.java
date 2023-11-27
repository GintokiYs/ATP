package com.hundsun.atp.servers;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.oas.annotations.EnableOpenApi;


/**
 * @author yeyh
 * @date 2023/09/26
 */
@SpringBootApplication(scanBasePackages = {"com.hundsun.atp"}, exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.hundsun.atp.persister.mapper")
@EnableOpenApi
@EnableTransactionManagement(order = 10)
@EnableScheduling
public class AtpApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtpApplication.class, args);
    }

}
