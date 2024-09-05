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
import java.time.LocalDateTime;
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
    private static final String REFRESH_UUID = "uuid";
    private static final String ISSUER = "zzansuni";


    public JwtToken generateToken(JwtUser jwtUser, String refreshUuid) {
        String accessToken = generateAccessToken(jwtUser);
        String refreshToken = generateRefreshToken(jwtUser, refreshUuid);
        LocalDateTime refreshTokenExpireAt = LocalDateTime.now().plusSeconds(REFRESH_TOKEN_EXPIRE_TIME/1000);
        return JwtToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshTokenExpireAt(refreshTokenExpireAt)
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


    public UserIdAndUuid validateAndGetUserIdAndUuid(String rawToken) {
        try {
            Claims claims = extractClaims(rawToken);
            Boolean isAccessToken = claims.get(IS_ACCESS_TOKEN, Boolean.class);
            if (isAccessToken == null || isAccessToken) {
                throw new UnauthorizedException("RefreshToken이 유효하지 않습니다.");
            }
            Long userId = Long.parseLong(claims.getSubject());
            String uuid = claims.get(REFRESH_UUID, String.class);
            return new UserIdAndUuid(userId, uuid);
        } catch (Exception e) {
            throw new UnauthorizedException("RefreshToken이 유효하지 않습니다.");
        }
    }

    public record UserIdAndUuid(
        Long userId,
        String uuid
    ){ }

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
     * 엑세스토큰 여부, 유저 id, role을 저장한다.
     */
    private String generateAccessToken(JwtUser jwtUser) {
        Date expireDate = new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME);
        return Jwts.builder()
                .signWith(secretKey)
                .claim(ROLE, jwtUser.getRole().toString())
                .claim(IS_ACCESS_TOKEN, true)
                .setSubject(jwtUser.getId().toString())
                .setExpiration(expireDate)
                .setIssuer(ISSUER)
                .compact();
    }

    /**
     * refreshToken 생성
     * 엑세스토큰 여부, uuid, 유저 아이디를 저장한다.
     */
    private String generateRefreshToken(JwtUser jwtUser, String refreshUuid) {
        Date expireDate = new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIME);
        return Jwts.builder()
                .signWith(secretKey)
                .claim(IS_ACCESS_TOKEN, false)
                .claim(REFRESH_UUID, refreshUuid)
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
