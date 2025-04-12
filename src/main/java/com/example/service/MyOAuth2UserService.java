/*
 * @Author: Xiaorui Wang
 * @Email: xiaorui.wang@usi.ch
 * @Date: 2025-04-12 08:09:26
 * @LastEditors: Xiaorui Wang
 * @LastEditTime: 2025-04-12 12:57:27
 * @Description: 
 * Copyright (c) 2025 by Xiaorui Wang, All Rights Reserved. 
 */
package com.example.service;

import com.example.model.User;
import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Service
@Transactional
public class MyOAuth2UserService extends DefaultOAuth2UserService {
    private final UserMapper userMapper;
    
    @Autowired
    public MyOAuth2UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getName();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        
        // Find user by email (since email is unique in our system)
        User user = userMapper.findByEmail(email);
        
        // If user doesn't exist, create new one
        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setPassword(""); // OAuth2 users don't have password
            user.setRole("USER");
            user.setProvider(provider);
            user.setProviderId(providerId);
            userMapper.insert(user);
        } 
        // If user exists but was not created with this OAuth provider, update provider info
        else if (user.getProvider() == null || !user.getProvider().equals(provider)) {
            user.setProvider(provider);
            user.setProviderId(providerId);
            userMapper.update(user);
        }
        
        return oAuth2User;
    }
}
