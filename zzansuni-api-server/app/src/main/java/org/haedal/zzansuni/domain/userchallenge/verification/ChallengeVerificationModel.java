package org.haedal.zzansuni.domain.userchallenge.verification;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChallengeVerificationModel {

    private final Long id;
    private final LocalDateTime createdAt;
    private final String content;
    private final String imageUrl;

    public static ChallengeVerificationModel from(ChallengeVerification challengeVerification) {
        return ChallengeVerificationModel.builder()
            .id(challengeVerification.getId())
            .createdAt(challengeVerification.getCreatedAt())
            .content(challengeVerification.getContent())
            .imageUrl(challengeVerification.getImageUrl())
            .build();
    }
}
