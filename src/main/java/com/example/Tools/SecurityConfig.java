/*
 * @Author: Xiaorui Wang
 * @Email: xiaorui.wang@usi.ch
 * @Date: 2025-04-05 13:56:26
 * @LastEditors: Xiaorui Wang
 * @LastEditTime: 2025-04-13 15:16:43
 * @Description: 
 * Copyright (c) 2025 by Xiaorui Wang, All Rights Reserved. 
 */
package com.example.Tools;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.service.MyOAuth2UserService;
import com.example.service.UserService;
import com.example.model.User;
import java.util.List;
import java.io.IOException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import java.util.Map;
import org.springframework.http.ResponseEntity;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private final MyOAuth2UserService myOAuth2UserService;
    @Autowired
    private final UserService userService;

    private static final List<String> PERMIT_ALL_PATHS = List.of(
            "/api/auth/login",
            "/api/auth/register",
            "/api/oauth2/**");

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/oauth2/**").permitAll()
                        .requestMatchers("/api/users/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.userService(myOAuth2UserService))
                        .successHandler(oAuth2AuthenticationSuccessHandler())
                        .failureHandler(oAuth2AuthenticationFailureHandler())
                        .authorizationEndpoint(authorization -> authorization
                                .baseUri("/api/oauth2/authorize"))
                        .redirectionEndpoint(redirection -> redirection
                                .baseUri("/api/oauth2/callback/*")))
                .with(new JwtConfigurer(jwtTokenProvider, PERMIT_ALL_PATHS), customizer -> {
                });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return username -> {
            User user = userService.getUserByEmail(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with email: " + username);
            }

            // Convert to UserDetails
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole())
                    .build();
        };
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter(jwtTokenProvider, PERMIT_ALL_PATHS);
    }

    @Bean
    public AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            try {
                // Get OAuth2 user details
                OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

                // Get email from OAuth2User
                String email = oAuth2User.getAttribute("email");
                if (email == null) {
                    String login = oAuth2User.getAttribute("login");
                    if (login != null) {
                        email = login + "@github.com";
                        System.out.println("Using GitHub login as email fallback: " + email);
                    } else {
                        throw new RuntimeException("Email is required for authentication");
                    }
                }

                // Get user from database
                User user = userService.getUserByEmail(email);
                if (user == null) {
                    throw new RuntimeException("User not found with email: " + email);
                }

                // Generate JWT token
                UserDetails userDetails = org.springframework.security.core.userdetails.User
                        .withUsername(user.getEmail())
                        .password(user.getPassword())
                        .roles(user.getRole())
                        .build();

                String token = jwtTokenProvider.generateToken(userDetails);

                // Create cookie with token
                ResponseCookie tokenCookie = ResponseCookie.from("access_token", token)
                        .httpOnly(true)
                        .secure(true)
                        .sameSite("Strict")
                        .path("/")
                        .maxAge(jwtTokenProvider.getExpirationTime() / 1000)
                        .build();

                // Add cookie to response
                response.addHeader(HttpHeaders.SET_COOKIE, tokenCookie.toString());

                // Get frontend URL
                String frontendUrl = System.getenv("FRONTEND_URL");
                if (frontendUrl == null || frontendUrl.isEmpty()) {
                    frontendUrl = "https://todocalendar.live";
                }

                // Add query parameters with user data
                frontendUrl += "?auth=success" +
                        "&id=" + user.getId() +
                        "&name=" + java.net.URLEncoder.encode(user.getName(), "UTF-8") +
                        "&email=" + java.net.URLEncoder.encode(user.getEmail(), "UTF-8") +
                        "&role=" + user.getRole();

                // Redirect to frontend
                response.sendRedirect(frontendUrl);
                System.out.println("Redirecting to frontend: " + frontendUrl);
            } catch (Exception e) {
                throw new RuntimeException("Error during OAuth2 authentication success", e);
            }
        };
    }

    @Bean
    public AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return (request, response, exception) -> {
            try {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"" + exception.getMessage() + "\"}");
                System.out.println("Error during OAuth2 authentication failure: " + exception.getMessage());
            } catch (IOException e) {
                throw new RuntimeException("Error during OAuth2 authentication failure", e);
            }
        };
    }

    // @Bean
    // public static org.springframework.security.web.DefaultRedirectStrategy
    // httpsRedirectStrategy() {
    // return new org.springframework.security.web.DefaultRedirectStrategy() {
    // @Override
    // public void sendRedirect(jakarta.servlet.http.HttpServletRequest request,
    // jakarta.servlet.http.HttpServletResponse response,
    // String url) throws IOException {
    // String httpsUrl = url.replace("http://", "https://");
    // super.sendRedirect(request, response, httpsUrl);
    // }
    // };
    // }
}
