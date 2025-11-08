package com.example.Santa.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

// JWT로 인증된 사용자 정보를 Spring Security의 인증 컨텍스트에 등록하기 위한 래퍼
public class MemberAuthentication extends UsernamePasswordAuthenticationToken {
    public MemberAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public static MemberAuthentication createMemberAuthentication(String username) {
        return new MemberAuthentication(username, null, null);
    }
}