package com.example.santa.config;

import com.example.santa.jwt.JwtTokenProvider;
import com.example.santa.service.AppUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final AppUserService appUserService;

    @Value("${app.oauth2.redirect-url}")
    private String redirectUrl;

/*
    @Value("${app.cookie.secure:false}")
    private boolean cookieSecure;

    @Value("${app.cookie.same-site:Lax}")
    private String sameSite;
*/

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
        //String a = URLEncoder.encode(accessToken, StandardCharsets.UTF_8);
        //String r = URLEncoder.encode(refreshToken, StandardCharsets.UTF_8);

/*
        // 쿠키 설정
        ResponseCookie accessCookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/")
                .maxAge(60 * 60) // 1시간
                .sameSite("None")
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/")
                .maxAge(60L * 60 * 24 * 7) // 7일
                .sameSite("None")
                .build();

        // 쿠키 추가
        response.addHeader("Set-Cookie", accessCookie.toString());
        response.addHeader("Set-Cookie", refreshCookie.toString());

        // 프론트 리다이렉트 (쿼리파라미터 대신 쿠키로 인증)
        try {
            response.sendRedirect(redirectUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

*/
        // 토큰을 프론트로 넘기는 방식 (URL fragment 사용)
        String redirectWithToken = redirectUrl + "#accessToken=" + accessToken;

        response.sendRedirect(redirectWithToken);
    }
}