package org.haedal.zzansuni.domain.challengegroup.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import org.haedal.zzansuni.domain.challengegroup.Challenge;
import org.haedal.zzansuni.domain.challengegroup.ChallengeCategory;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroup;
import org.haedal.zzansuni.domain.challengegroup.DayType;
import org.haedal.zzansuni.domain.userchallenge.UserChallenge;
import org.haedal.zzansuni.domain.userchallenge.ChallengeVerification;

@Getter
@Builder
public class ChallengeModel {


    @Builder
    public record Main(
        Long id,
        Integer requiredCount,
        DayType dayType,
        Integer onceExp,
        Integer successExp,
        Integer difficulty,
        LocalDate startDate,
        LocalDate endDate
    ) {
        public static Main from(Challenge challenge) {
            return Main.builder()
                .id(challenge.getId())
                .requiredCount(challenge.getRequiredCount())
                .dayType(challenge.getDayType())
                .onceExp(challenge.getOnceExp())
                .successExp(challenge.getSuccessExp())
                .difficulty(challenge.getDifficulty())
                .startDate(challenge.getStartDate())
                .endDate(challenge.getEndDate())
                .build();
        }
    }


    @Builder
    public record ChallengeVerificationResult(Integer totalCount, Integer successCount,
                                              Integer obtainExp) {

        public static ChallengeVerificationResult of(Integer totalCount, Integer successCount,
            Integer obtainExp) {
            return ChallengeVerificationResult.builder()
                .totalCount(totalCount)
                .successCount(successCount)
                .obtainExp(obtainExp)
                .build();
        }

        public static ChallengeVerificationResult from(Integer totalCount, Integer successCount,
            Integer obtainExp) {
            return ChallengeVerificationResult.builder()
                .totalCount(totalCount)
                .successCount(successCount)
                .obtainExp(obtainExp)
                .build();
        }
    }


    @Builder
    public record ChallengeRecord(
            String title,
            Integer totalCount,
            Integer successCount,
            LocalDate startDate,
            LocalDate endDate,
            List<Long> recordIds
    ) {

        public static ChallengeRecord from(
            Challenge challenge,
            List<ChallengeVerification> challengeVerificationList
        ) {
            ChallengeGroup challengeGroup = challenge.getChallengeGroup();
            return ChallengeRecord.builder()
                .title(challengeGroup.getTitle())
                .totalCount(challenge.getRequiredCount())
                .successCount(challengeVerificationList.size())
                .startDate(challenge.getStartDate())
                .endDate(challenge.getEndDate())
                .recordIds(challengeVerificationList.stream()
                    .map(ChallengeVerification::getId)
                    .collect(Collectors.toList()))
                .build();
        }
    }

    @Builder
    public record ChallengeCurrent(
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

        public static ChallengeCurrent from(UserChallenge userChallenge, Boolean reviewWritten) {
            Challenge challenge = userChallenge.getChallenge();
            return ChallengeCurrent.builder()
                    .challengeId(challenge.getId())
                    .title(challenge.getChallengeGroup().getTitle())
                    .totalCount(challenge.getRequiredCount())
                    .successCount(userChallenge.getChallengeVerifications().size())
                    .participationDate(userChallenge.getCreatedAt())
                    .startDate(challenge.getStartDate())
                    .endDate(challenge.getEndDate())
                    .category(challenge.getChallengeGroup().getCategory())
                    .reviewWritten(reviewWritten)
                    .build();
        }

    }

    @Builder
    public record ChallengeComplete(
        Long challengeId,
        String title,
        LocalDate successDate,
        ChallengeCategory category,
        Boolean reviewWritten
    ) {

        public static ChallengeComplete from(UserChallenge userChallenge, Boolean reviewWritten
        ) {
            Challenge challenge = userChallenge.getChallenge();

            return ChallengeComplete.builder()
                .challengeId(challenge.getId())
                .title(challenge.getChallengeGroup().getTitle())
                // 성공한 날짜는 가장 최근에 인증한 날짜로 설정
                .successDate(userChallenge.getSuccessDate().orElse(null))
                .category(challenge.getChallengeGroup().getCategory())
                .reviewWritten(reviewWritten)
                .build();
        }

    }

}
