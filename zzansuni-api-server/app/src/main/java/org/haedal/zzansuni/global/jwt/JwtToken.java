package org.haedal.zzansuni.global.jwt;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class JwtToken {
    private String accessToken;
    private String refreshToken;
    private LocalDateTime refreshTokenExpireAt;

    /**
     * 유효한 토큰을 나타내는 VO
     * 검증이 완료된 JWT를 저장
     */
    public static class ValidToken {
        private final String rawToken;

        public ValidToken(String rawToken) {
            this.rawToken = rawToken;
        }

        public static ValidToken of(String rawToken) {
            return new ValidToken(rawToken);
        }


        public String getValue() {
            return rawToken;
        }
    }
}
