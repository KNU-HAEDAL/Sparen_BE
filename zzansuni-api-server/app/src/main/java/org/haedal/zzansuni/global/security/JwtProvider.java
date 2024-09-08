package org.haedal.zzansuni.global.security;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.global.jwt.JwtToken;
import org.haedal.zzansuni.global.jwt.JwtUser;
import org.haedal.zzansuni.global.jwt.JwtUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtProvider implements AuthenticationProvider {
    private final JwtUtils jwtUtils;
    private static final String ROLE_PREFIX = "ROLE_";

    /**
     * JwtAuthenticationToken을 받아서 인증을 진행
     * 토큰이 유효하지 않은 경우 예외를 발생시킴
     * 유효한경우 SecurityContextHolder에 인증정보를 저장
     * 그후 UsernamePasswordAuthenticationToken을 반환
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();

        // 토큰을 검증하는 단계
        if (!jwtUtils.validateAccessToken(token)) {
            throw new AuthenticationServiceException("유효하지 않은 토큰입니다.");
        }
        JwtUser jwtUser = jwtUtils.getJwtUser(JwtToken.ValidToken.of(token));

        // 검증 후 인증정보 Authentication 객체를 반환
        return new UsernamePasswordAuthenticationToken(jwtUser, null, getAuthorities(jwtUser));
    }

    private Set<SimpleGrantedAuthority> getAuthorities(JwtUser jwtUser) {
        return Set.of(new SimpleGrantedAuthority(ROLE_PREFIX + jwtUser.getRole().name()));
    }

    /**
     * JwtAuthenticationToken 타입의 토큰을 지원하는 경우에만 인증을 진행
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtAuthenticationToken.class);
    }
}
