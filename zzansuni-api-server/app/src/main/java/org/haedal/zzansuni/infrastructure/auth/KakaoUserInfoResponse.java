package org.haedal.zzansuni.infrastructure.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoUserInfoResponse(
        Long id,
        @JsonProperty("connected_at")
        String connectedAt,
        Properties properties,
        @JsonProperty("kakao_account")
        KakaoAccount kakaoAccount
) {

    public record Properties(
            String nickname
    ) {
    }
    public record KakaoAccount(

            String nickname,
            @JsonProperty("is_default_nickname")
            Boolean isDefaultNickname
    ) {
    }
}
