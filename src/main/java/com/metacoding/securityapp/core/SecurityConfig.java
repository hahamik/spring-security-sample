package com.metacoding.securityapp.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // csrf 필터 비활성화
        http.csrf(configure -> configure.disable());

        // 만약 다른 페이지 요청하다가 여기로 쫒겨나면 인증 후 그 화면으로 다시 보내준다(return).
        http.formLogin(form -> form
                .loginPage("/login-form") // 로그인 페이지는 여기로
                .loginProcessingUrl("/login") // 여기로 요청-> 인가를 처리하는 url 설정, username=ssar&password=1234 
                .defaultSuccessUrl("/main")); // 성공하면 여기로

        http.authorizeHttpRequests(
                authorize -> authorize
                        .requestMatchers("/user/**", "/main") // 인증이 필요한 주소, 세션 holder에 있는지 확인하고 없으면 로그인 페이지로 보냄
                        .authenticated() // 위 주소는 인증되어야 하용할 수 있음.
                        .anyRequest() // 그외 다른 요청들
                        .permitAll() // 다 허가
        );

        return http.build();
    }
}
