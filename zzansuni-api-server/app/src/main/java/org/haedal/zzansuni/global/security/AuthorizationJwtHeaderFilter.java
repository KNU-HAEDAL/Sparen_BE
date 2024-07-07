package org.haedal.zzansuni.global.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;


/**
 * Request Header에서 Authorization을 확인하여 JWT 토큰을 추출하고 인증을 시도하는 필터
 * BasicAuthenticationFilter를 상속받아 구현
 * JwtAuthenticationToken을 생성하여 JwtProvider에게 인증을 요청한다.
 */
public class AuthorizationJwtHeaderFilter extends BasicAuthenticationFilter {
    public AuthorizationJwtHeaderFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if(header == null || !header.startsWith("Bearer") || header.split(" ").length != 2){
            chain.doFilter(request, response);
            return;
        }

        String rawToken = header.split(" ")[1];

        var authReq = new JwtAuthenticationToken(rawToken);
        Authentication authRes = super.getAuthenticationManager().authenticate(authReq);

        SecurityContextHolder.getContext().setAuthentication(authRes);

        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // /api/auth로 시작하는 요청은 필터를 거치지 않는다.
        return request.getServletPath().startsWith("/api/auth");
    }
}
