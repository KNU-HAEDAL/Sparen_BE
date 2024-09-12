package org.haedal.zzansuni.userchallenge.domain.application;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.haedal.zzansuni.userchallenge.domain.ChallengeVerification;
import org.haedal.zzansuni.userchallenge.domain.ChallengeVerificationStatus;

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


    @Builder
    public record Admin(
            Long verificationId,
            String content,
            String imageUrl,
            ChallengeVerificationStatus status,
            LocalDateTime createdAt,
            Long ChallengeGroupId,
            String ChallengeGroupTitle,
            Long UserId,
            String UserNickname,
            String UserImageUrl
    ) {

        public static ChallengeVerificationModel.Admin from(ChallengeVerification challengeVerification) {
            return Admin.builder()
                    .verificationId(challengeVerification.getId())
                    .content(challengeVerification.getContent())
                    .imageUrl(challengeVerification.getImageUrl())
                    .status(challengeVerification.getStatus())
                    .createdAt(challengeVerification.getCreatedAt())
                    .ChallengeGroupId(challengeVerification.getUserChallenge().getChallenge().getChallengeGroup().getId())
                    .ChallengeGroupTitle(challengeVerification.getUserChallenge().getChallenge().getChallengeGroup().getTitle())
                    .UserId(challengeVerification.getUserChallenge().getUser().getId())
                    .UserNickname(challengeVerification.getUserChallenge().getUser().getNickname())
                    .UserImageUrl(challengeVerification.getUserChallenge().getUser().getProfileImageUrl())
                    .build();
        }
    }
}
