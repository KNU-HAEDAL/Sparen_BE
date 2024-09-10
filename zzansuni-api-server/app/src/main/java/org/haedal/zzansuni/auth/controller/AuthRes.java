package org.haedal.zzansuni.auth.controller;

import lombok.Builder;
import org.haedal.zzansuni.user.controller.UserRes;
import org.haedal.zzansuni.user.domain.UserModel;
import org.haedal.zzansuni.global.jwt.JwtToken;

public class AuthRes {
    @Builder
    public record LoginResponse(
            String accessToken,
            String refreshToken,
            UserRes.UserInfo userInfo
    ) {
        public static LoginResponse from(JwtToken jwtToken, UserModel.Main userMain) {
            var userInfo = UserRes.UserInfo.from(userMain);
            return LoginResponse.builder()
                    .accessToken(jwtToken.getAccessToken())
                    .refreshToken(jwtToken.getRefreshToken())
                    .userInfo(userInfo)
                    .build();
        }
    }

    @Builder
    public record JwtResponse(
            String accessToken,
            String refreshToken
    ) {
        public static JwtResponse from(JwtToken jwtToken) {
            return JwtResponse.builder()
                    .accessToken(jwtToken.getAccessToken())
                    .refreshToken(jwtToken.getRefreshToken())
                    .build();
        }
    }
}
