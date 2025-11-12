package com.example.santa.service;

import com.example.santa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // 토큰 확인 - 나중에 엑세스 토큰 출력은 지워야함
        String accessToken = userRequest.getAccessToken().getTokenValue();
        System.out.println("Access Token: " + accessToken);

        // 소셜에서 사용자 정보 로드
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 사용자 별 이메일 추출
        String email = oAuth2User.getAttribute("email");

        // OAuth provider에 email 없으면 실패
        if (email == null || email.isBlank()) {
            throw new OAuth2AuthenticationException(
                    new OAuth2Error("invalid_user_info"),
                    "Email not provided by OAuth2 provider");
        }

        // Member DB에 이메일 없으면 실패
        boolean exists = memberRepository.existsByEmail(email);
        if (!exists) {
            throw new OAuth2AuthenticationException(
                    new OAuth2Error("unauthorized_client"),
                    "This email is not allowed");
        }

        // User 권한 부여
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                oAuth2User.getAttributes(),
                "email");
    }
}
