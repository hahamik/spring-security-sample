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
        // h2 접속이 안됨 이걸 설정해야 접속 가능(same origin 설정)
        // h2 console은 iframe으로 동작하므로, 이 설정이 필요
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        // csrf 필터 비활성화
        http.csrf(configure -> configure.disable());

        // 만약 다른 페이지 요청하다가 여기로 쫒겨나면 인증 후 그 화면으로 다시 보내준다(return).
        http.formLogin(form -> form
                        .loginPage("/login-form") // 로그인 페이지는 여기로
                        .loginProcessingUrl("/login") // 여기로 요청-> 인가를 처리하는 url 설정, username=ssar&password=1234
//                .usernameParameter() //<< 여기다가 email이라고 적으면 username의 key값을 변경 가능함
                        .defaultSuccessUrl("/main")
        ); // 성공하면 여기로

        http.authorizeHttpRequests(
                authorize -> authorize
                        .requestMatchers("/main") // 인증이 필요한 주소, 세션 holder에 있는지 확인하고 없으면 로그인 페이지로 보냄
                        .authenticated() // 위 주소는 인증되어야 하용할 수 있음.
                        .requestMatchers("/user/**")
                        .hasRole("USER")
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")// 여기다가 hasRoles로 여러 권한들을 줄 수 있지만 그렇게 안함 보통 권한을 여러개 주고 그 권한을 설정하지 여기다 권한 여러개 안 줌. 어떤 한 권한에 모든 권한을 주는건 안 좋음(관리가 힘듦) , 각 페이지마다 권한을 나누고 그 권한을 다 주는게 좋음.
                        .anyRequest() // 그외 다른 요청들
                        .permitAll() // 다 허가
        );


        return http.build();
    }
}
