package com.ann.annovation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 이 파일이 스프링의 환경 설정 파일임을 의미하는 어노테이션
@EnableWebSecurity // 모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 어노테이션 (스프링 시큐리티 활성화)
public class SecurityConfig {
    @Bean // 스프링 시큐리티의 세부 설정 (SecurityFilterChain 빈을 생성하여 설정)
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 로그인하지 않더라도 모든 페이지에 접근할 수 있도록 인증되지 않은 모든 페이지의 요청을 허락
        http
            .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
              .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
        ;
        return http.build();
    }
}