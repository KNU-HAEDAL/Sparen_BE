package org.haedal.zzansuni.auth.domain;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.haedal.zzansuni.common.domain.UuidHolder;
import org.haedal.zzansuni.global.jwt.JwtToken;
import org.haedal.zzansuni.global.jwt.JwtUser;
import org.haedal.zzansuni.global.jwt.JwtUtils;
import org.haedal.zzansuni.user.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateJwtUseCase {
    private final JwtUtils jwtUtils;
    private final RefreshTokenStore refreshTokenStore;
    private final UuidHolder uuidHolder;
    /**
     * JWT 발급
     * 1. 리프래시토큰의 uuid 생성
     * 2. JWT 토큰 생성
     * 3. DB에 리프래시토큰 정보를 저장
     */
    @Transactional
    public JwtToken invoke(User user) {
        JwtUser jwtUser = JwtUser.of(user.getId(), user.getRole());
        String uuid = uuidHolder.random();
        JwtToken jwtToken = jwtUtils.generateToken(jwtUser, uuid);
        RefreshToken refreshToken = RefreshToken.create(uuid, user, jwtToken.getRefreshTokenExpireAt());
        refreshTokenStore.flushSave(refreshToken);
        return jwtToken;
    }
}
