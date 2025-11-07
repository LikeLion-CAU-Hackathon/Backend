package com.example.Santa.config;

import com.example.Santa.jwt.JwtTokenProvider;
import com.example.Santa.service.AppUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final AppUserService appUserService;

    @Value("${app.oauth2.redirect-url:http://localhost:3000/oauth/callback}")
    private String redirectUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");

        appUserService.createUser(email);
        // JWT 생성
        String accessToken = jwtTokenProvider.generateAccessToken(email);
        String refreshToken = jwtTokenProvider.generateRefreshToken(email);


        // URL 인코딩
        String a = URLEncoder.encode(accessToken, StandardCharsets.UTF_8);
        String r = URLEncoder.encode(refreshToken, StandardCharsets.UTF_8);

        // 간단한 리다이렉트 방식
        String url = String.format("%s?access=%s&refresh=%s", redirectUrl, a, r);
        response.sendRedirect(url);
    }
}