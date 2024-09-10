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

import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateJwtUseCase {
    private final JwtUtils jwtUtils;
    private final RefreshTokenReader refreshTokenReader;
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

        LocalDateTime expiredAt = jwtToken.getRefreshTokenExpireAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        RefreshToken refreshToken = RefreshToken.create(uuid, user, expiredAt);
        refreshTokenStore.flushSave(refreshToken);
        return jwtToken;
    }

    @Transactional
    public JwtToken removeRefreshTokenAndCreateJwt(JwtUtils.UserIdAndUuid userIdAndUuid) {
        RefreshToken refreshToken = refreshTokenReader.findById(userIdAndUuid.uuid())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 토큰입니다."));

        // jwtUtils에서 이미 검증하였으나, 방어적으로 다시 한번 검증
        if (!refreshToken.getUser().getId().equals(userIdAndUuid.userId())) {
            throw new IllegalArgumentException("토큰의 유저정보가 일치하지 않습니다.");
        } else if (refreshToken.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("만료된 토큰입니다.");
        }

        refreshTokenStore.delete(refreshToken.getId());
        User user = refreshToken.getUser();
        return invoke(user);
    }
}
