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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtProvider implements AuthenticationProvider {
    private final JwtUtils jwtUtils;

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
        if(!jwtUtils.validateToken(token)){
            throw new AuthenticationServiceException("유효하지 않은 토큰입니다.");
        }
        JwtUser jwtUser = jwtUtils.getJwtUser(JwtToken.ValidToken.of(token));
        Set<SimpleGrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(jwtUser.getRole().name()));

        // 검증 후 SecurityContextHolder에 인증정보를 저장
        Authentication jwtUserToken = new UsernamePasswordAuthenticationToken(jwtUser, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(jwtUserToken);

        return jwtUserToken;
    }

    /**
     * JwtAuthenticationToken 타입의 토큰을 지원하는 경우에만 인증을 진행
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtAuthenticationToken.class);
    }
}
