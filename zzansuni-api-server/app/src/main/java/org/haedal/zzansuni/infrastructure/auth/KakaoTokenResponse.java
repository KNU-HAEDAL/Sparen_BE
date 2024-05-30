package org.haedal.zzansuni.infrastructure.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoTokenResponse(
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("token_type")
        String tokenType,
        @JsonProperty("refresh_token")
        String refreshToken,
        @JsonProperty("expires_in")
        long expiresIn,
        String scope,
        @JsonProperty("refresh_token_expires_in")
        long refreshTokenExpiresIn
) {
}
