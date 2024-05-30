package org.haedal.zzansuni.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.haedal.zzansuni.global.exception.UnauthorizedException;
import org.haedal.zzansuni.global.security.Role;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils implements InitializingBean {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-expire-time}")
    private long ACCESS_TOKEN_EXPIRE_TIME;
    @Value("${jwt.refresh-token-expire-time}")
    private long REFRESH_TOKEN_EXPIRE_TIME;

    private Key secretKey;
    private static final String ROLE = "role";
    private static final String IS_ACCESS_TOKEN = "isAccessToken";

    private static final String ISSUER = "zzansuni";


    public JwtToken createToken(JwtUser jwtUser) {
        String accessToken = generateToken(jwtUser, true);
        String refreshToken = generateToken(jwtUser, false);
        return JwtToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * Jwt가 유효한지 검사하는 메서드.
     * 만료시간, 토큰의 유효성을 검사한다.
     */
    public boolean validateToken(String rawToken) {
        try {
            Claims claims = extractClaims(rawToken);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {//JwtException, ExpiredJwtException, NullPointerException
            return false;
        }
    }

    /**
     * [validateToken] 이후 호출하는 메서드.
     * refreshToken을 통해, accessToken을 재발급하는 메서드.
     * refreshToken의 유효성을 검사하고, isAccessToken이 true일때만 accessToken을 재발급한다.
     */
    public String reissueAccessToken(JwtToken.ValidToken refreshToken) {
        Claims claims = extractClaims(refreshToken.getValue());
        if (claims.get(IS_ACCESS_TOKEN, Boolean.class)) {
            throw new UnauthorizedException("RefreshToken이 유효하지 않습니다.");
        }
        JwtUser jwtUser = claimsToJwtUser(claims);
        return generateToken(jwtUser, true);

    }

    /**
     * [validateToken] 이후 호출하는 메서드.
     * rawToken을 통해 JwtUser를 추출한다.
     * [jwtUser]는 userId와 role을 가지고 있다. 즉 JWT에 저장된 정보를 추출한다.
     */
    public JwtUser getJwtUser(JwtToken.ValidToken token) {
        Claims claims = extractClaims(token.getValue());
        return claimsToJwtUser(claims);
    }

    private JwtUser claimsToJwtUser(Claims claims) {
        Role userRole = Role.valueOf(claims.get(ROLE, String.class));
        return JwtUser.of(Long.parseLong(claims.getSubject()), userRole);
    }


    /**
     * Jwt 토큰생성
     * accessToken과 refreshToken의 다른점은 만료시간과, isAccessToken이다.
     */
    private String generateToken(JwtUser jwtUser, boolean isAccessToken) {
        long expireTime = isAccessToken ? ACCESS_TOKEN_EXPIRE_TIME : REFRESH_TOKEN_EXPIRE_TIME;
        Date expireDate = new Date(System.currentTimeMillis() + expireTime);
        return Jwts.builder()
                .signWith(secretKey)
                .claim(ROLE, jwtUser.getRole().toString())
                .claim(IS_ACCESS_TOKEN, isAccessToken)
                .setSubject(jwtUser.getId().toString())
                .setExpiration(expireDate)
                .setIssuer(ISSUER)
                .compact();
    }


    private Claims extractClaims(String rawToken) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(rawToken)
                .getBody();
    }

    /**
     * InitializingBean을 구현하여, Bean이 생성된 후 secretKey를 생성한다.
     * HS256방식의 키를 생성한다.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        secretKey = new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }
}
