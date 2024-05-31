package org.haedal.zzansuni.infrastructure.auth;

public record NaverUserInfoResponse(
        String resultcode,
        String message,
        NaverUserInfoResponseResponse response
) {

    public record NaverUserInfoResponseResponse(
            String id,
            String nickname
    ) {
    }
}
