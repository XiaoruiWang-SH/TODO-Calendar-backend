/*
 * @Author: Xiaorui Wang
 * @Email: xiaorui.wang@usi.ch
 * @Date: 2025-04-07 08:56:01
 * @LastEditors: Xiaorui Wang
 * @LastEditTime: 2025-04-08 15:34:49
 * @Description: 
 * Copyright (c) 2025 by Xiaorui Wang, All Rights Reserved. 
 */
package com.example.Tools;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.util.List;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtTokenProvider jwtTokenProvider;
    private final List<String> permitAllPaths;

    public JwtConfigurer(JwtTokenProvider jwtTokenProvider, List<String> permitAllPaths) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.permitAllPaths = permitAllPaths;
    }

    @Override
    public void configure(HttpSecurity builder) {
        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(jwtTokenProvider, permitAllPaths);
        builder.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
} 