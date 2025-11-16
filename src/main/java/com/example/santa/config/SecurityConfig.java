package com.example.santa.config;

import com.example.santa.jwt.JwtAuthenticationFilter;
import com.example.santa.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtAuthenticationFilter jwtFilter;
    private final OAuthSuccessHandler oAuthSuccessHandler; // 로그인 성공 시 JWT 발급 및 리다이렉트

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(SecurityConfig::corsAllow)
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                //.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                // 접근 제어
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/oauth2/**",
                                "/login/oauth2/**", // 소셜 로그인 경로
                                "/oauth/callback",
                                "/h2-console/**",   // H2 콘솔
                                "/error",           // 에러 페이지
                                "/swagger-ui.html",  // Swagger UI 메인 페이지
                                "/swagger-ui/**",    // Swagger UI 리소스 (css, js...)
                                "/v3/api-docs/**",   // OpenAPI 명세서 (JSON)
                                "/webjars/swagger-ui/**" // 웹jar 리소스
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                // OAuth2 로그인 > 사용자 정보 로딩 > 성공시 JWT 토큰 발급
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        .successHandler(oAuthSuccessHandler)
                );


        // JWT 필터 등록 (모든 보호 자원 접근 전 토큰 검증)
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private static void corsAllow(CorsConfigurer<HttpSecurity> corsConfigurer) {
        corsConfigurer.configurationSource(request -> {
            CorsConfiguration configuration = new CorsConfiguration();

            configuration.setAllowedMethods(Collections.singletonList("*")); // 모든 메서드 허용
            configuration.setAllowedOrigins(Arrays.asList(
                    "http://localhost:3000",
                    "http://localhost:5173",
                    "https://hackathon-santa.p-e.kr",   // 프론트가 이 도메인/서브도메인이라면 추가
                    "https://chungkathon.netlify.app",
                    "https://chungkathondevelop.netlify.app"
            ));
            configuration.setAllowedHeaders(Collections.singletonList("*")); // 모든 헤더 허용
            configuration.setAllowCredentials(true);
            configuration.setMaxAge(3600L); // 1시간(3600초) 동안 오는 요청이 처리됨

            return configuration;
        });
    }
}