package com.be05.sns.config;

import com.be05.sns.token.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 보안 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authHttp -> authHttp
                        .requestMatchers("/upload/profile")
                        .authenticated() // 인증된 사용자만
                        .requestMatchers("/login", "/register")
                        // 모든 접근 허용(나머지 인증 후 허용)
                        .anonymous().anyRequest().authenticated()

                ).sessionManagement(sessionManagement -> sessionManagement
                        // 세션 (생성 X, STATELESS), JWT 토큰 기반 인증을 사용하기 위한 설정
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // AuthorizationFilter 전 jwtTokenFilter
                ).addFilterBefore(jwtFilter, AuthorizationFilter.class);

        return http.build();
    }
}
