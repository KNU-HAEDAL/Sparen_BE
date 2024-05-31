package org.haedal.zzansuni.domain.challengegroup.challenge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroup;
import org.haedal.zzansuni.domain.challengegroup.DayType;

@Getter
@Builder
public class ChallengeModel {

    private Long id;
    private ChallengeGroup challengeGroup;
    private Integer requiredCount;
    private DayType dayType;
    private Integer onceExp;
    private Integer successExp;
    private Integer difficulty;

    public static ChallengeModel from(Challenge challenge) {
        return ChallengeModel.builder()
            .id(challenge.getId())
            .challengeGroup(challenge.getChallengeGroup())
            .requiredCount(challenge.getRequiredCount())
            .dayType(challenge.getDayType())
            .onceExp(challenge.getOnceExp())
            .successExp(challenge.getSuccessExp())
            .difficulty(challenge.getDifficulty())
            .build();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ChallengeVerificationResult {

        private Integer totalCount;
        private Integer successCount;
        private Integer obtainExp;

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
