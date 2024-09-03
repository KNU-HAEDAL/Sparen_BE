package org.haedal.zzansuni.challengegroup.domain.application;

import lombok.Builder;
import lombok.Getter;
import org.haedal.zzansuni.challengegroup.domain.Challenge;

import java.time.LocalDate;

@Getter
@Builder
public class ChallengeModel {


    @Builder
    public record Main(
        Long id,
        Integer requiredCount,
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
                .onceExp(challenge.getOnceExp())
                .successExp(challenge.getSuccessExp())
                .difficulty(challenge.getDifficulty())
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




}
