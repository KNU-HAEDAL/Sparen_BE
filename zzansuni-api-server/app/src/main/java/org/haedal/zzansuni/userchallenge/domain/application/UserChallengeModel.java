package org.haedal.zzansuni.userchallenge.domain.application;

import lombok.Builder;
import org.haedal.zzansuni.challengegroup.domain.Challenge;
import org.haedal.zzansuni.challengegroup.domain.ChallengeCategory;
import org.haedal.zzansuni.challengegroup.domain.ChallengeGroup;
import org.haedal.zzansuni.userchallenge.domain.ChallengeVerification;
import org.haedal.zzansuni.userchallenge.domain.UserChallenge;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class UserChallengeModel {
    @Builder
    public record Record(
            String title,
            Integer totalCount,
            Integer successCount,
            LocalDate startDate,
            LocalDate endDate,
            List<Long> recordIds
    ) {

        public static Record from(
                Challenge challenge,
                List<ChallengeVerification> challengeVerificationList
        ) {
            ChallengeGroup challengeGroup = challenge.getChallengeGroup();
            return Record.builder()
                    .title(challengeGroup.getTitle())
                    .totalCount(challenge.getRequiredCount())
                    .successCount(challengeVerificationList.size())
                    .recordIds(challengeVerificationList.stream()
                            .map(ChallengeVerification::getId)
                            .collect(Collectors.toList()))
                    .build();
        }
    }

    @Builder
    public record Current(
            Long challengeId,
            String title,
            Integer totalCount,
            Integer successCount,
            LocalDateTime participationDate,
            LocalDate startDate,
            LocalDate endDate,
            ChallengeCategory category,
            Boolean reviewWritten
    ) {

        public static Current from(UserChallenge userChallenge, Boolean reviewWritten) {
            Challenge challenge = userChallenge.getChallenge();
            return Current.builder()
                    .challengeId(challenge.getId())
                    .title(challenge.getChallengeGroup().getTitle())
                    .totalCount(challenge.getRequiredCount())
                    .successCount(userChallenge.getChallengeVerifications().size())
                    .participationDate(userChallenge.getCreatedAt())
                    .category(challenge.getChallengeGroup().getCategory())
                    .reviewWritten(reviewWritten)
                    .build();
        }

    }

    @Builder
    public record Complete(
            Long challengeId,
            String title,
            LocalDate successDate,
            ChallengeCategory category,
            Boolean reviewWritten
    ) {

        public static Complete from(UserChallenge userChallenge, Boolean reviewWritten
        ) {
            Challenge challenge = userChallenge.getChallenge();

            return Complete.builder()
                    .challengeId(challenge.getId())
                    .title(challenge.getChallengeGroup().getTitle())
                    // 성공한 날짜는 가장 최근에 인증한 날짜로 설정
                    .successDate(userChallenge.getRecentSuccessDate().orElse(null))
                    .category(challenge.getChallengeGroup().getCategory())
                    .reviewWritten(reviewWritten)
                    .build();
        }

    }
}
