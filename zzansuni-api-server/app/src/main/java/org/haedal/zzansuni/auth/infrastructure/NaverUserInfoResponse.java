package org.haedal.zzansuni.auth.infrastructure;

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
