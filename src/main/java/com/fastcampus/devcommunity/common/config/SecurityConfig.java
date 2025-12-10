//package com.fastcampus.devcommunity.common.config;
//
//import com.fastcampus.devcommunity.domain.user.service.CustomOAuth2UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final CustomOAuth2UserService customOAuth2UserService;
//
//    @Bean
//    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/", "/login", "/css/**", "/js/**", "/images/**", "/oauth2/**").permitAll()
//                        .requestMatchers("/api/v1/posts/**").permitAll()   // ★ 추가
//                        .anyRequest().permitAll()
//                )
//                .oauth2Login(oauth -> oauth
//                        .loginPage("/login") // 로그인 버튼 누르면 오는 페이지
//                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
//                        .defaultSuccessUrl("/", true)
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/")
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")
//                );
//
//        return http.build();
//    }
//}
