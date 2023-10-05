package com.hundsun.atp.servers;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;


/**
 * @author yeyh
 * @date 2023/09/26
 */
@SpringBootApplication(scanBasePackages = {"com.hundsun.atp"})
@MapperScan("com.hundsun.atp.persister.mapper")
@EnableOpenApi
public class AtpApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtpApplication.class, args);
    }

}
