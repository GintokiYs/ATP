package com.hundsun.atp.servers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

//@Configuration
//public class CorsConfig {
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("http://10.20.145.92:8084")
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                        .allowedHeaders("Content-Type", "Authorization")
//                        .allowCredentials(true);
//            }
//        };
//    }
//}

//@Configuration
//public class CorsConfig {
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.addAllowedOrigin("http://10.20.145.92:8084"); // 允许的前端应用地址
//        config.addAllowedMethod("*"); // 允许所有 HTTP 方法
//        config.addAllowedHeader("*"); // 允许所有请求头
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
//}