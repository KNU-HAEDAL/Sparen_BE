package org.haedal.zzansuni.domain.challengegroup.challenge;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.haedal.zzansuni.domain.challengegroup.ChallengeCategory;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroup;
import org.haedal.zzansuni.domain.challengegroup.DayType;
import org.haedal.zzansuni.domain.challengegroup.challengeverification.ChallengeVerification;
import org.haedal.zzansuni.domain.challengegroup.userchallenge.UserChallenge;

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

    @Builder
    public record ChallengeVerificationResult(Integer totalCount, Integer successCount,
                                              Integer obtainExp) {

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
    public record ChallengeRecordDto(String title, Integer totalCount, Integer successCount,
                                     LocalDate startDate, LocalDate endDate, List<Long> recordIds) {

        public static ChallengeRecordDto from(Challenge challenge, ChallengeGroup challengeGroup,
            List<ChallengeVerification> challengeVerificationList) {
            return ChallengeRecordDto.builder()
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


}
