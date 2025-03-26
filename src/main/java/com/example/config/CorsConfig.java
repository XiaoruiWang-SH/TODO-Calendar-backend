/*
 * @Author: Xiaorui Wang
 * @Email: xiaorui.wang@usi.ch
 * @Date: 2025-03-26 18:36:57
 * @LastEditors: Xiaorui Wang
 * @LastEditTime: 2025-03-26 18:36:59
 * @Description: 
 * Copyright (c) 2025 by Xiaorui Wang, All Rights Reserved. 
 */
package com.example.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 匹配所有路径
                .allowedOrigins("http://localhost:5173") // 允许前端源
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的方法，包括 OPTIONS
                .allowedHeaders("*") // 允许所有头
                .allowCredentials(true) // 支持凭证（如 Cookie）
                .maxAge(3600); // 预检请求缓存时间
    }
}
