package org.haedal.zzansuni.userchallenge.domain.application;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.haedal.zzansuni.userchallenge.domain.ChallengeVerification;

@Getter
@Builder
public class ChallengeVerificationModel {
    @Getter
    @Builder
    public static class Main {
        private Long id;
        private LocalDateTime createdAt;
        private String content;
        private String imageUrl;

        public static ChallengeVerificationModel.Main from(ChallengeVerification challengeVerification) {
            return ChallengeVerificationModel.Main.builder()
                    .id(challengeVerification.getId())
                    .createdAt(challengeVerification.getCreatedAt())
                    .content(challengeVerification.getContent())
                    .imageUrl(challengeVerification.getImageUrl())
                    .build();
        }
    }
}
