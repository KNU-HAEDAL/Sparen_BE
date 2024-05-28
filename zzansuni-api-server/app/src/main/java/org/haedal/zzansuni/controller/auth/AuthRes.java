package org.haedal.zzansuni.controller.auth;

import lombok.Builder;
import org.haedal.zzansuni.controller.user.UserRes;

public class AuthRes {
    @Builder
    public record LoginResponse(
            String accessToken,
            String refreshToken,
            UserRes.UserInfoDto userInfo
    ) {
    }
}
