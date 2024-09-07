package org.haedal.zzansuni.challengegroup.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.haedal.zzansuni.challengegroup.domain.ChallengeCategory;
import org.haedal.zzansuni.challengegroup.domain.ChallengeGroupCommand;

import java.time.LocalDate;
import java.util.List;

public class ChallengeGroupReq {
    public record Update(
            @NotNull(message = "id는 필수값입니다.")
            Long id,
            @NotBlank(message = "title은 필수값입니다.")
            String title,
            @NotBlank(message = "content는 필수값입니다.")
            String content,
            @NotBlank(message = "guide는 필수값입니다.")
            String guide,
            @NotNull(message = "category는 필수값입니다.")
            ChallengeCategory category,
            @NotNull(message = "joinStartDate는 필수값입니다.")
            LocalDate joinStartDate,
            @NotNull(message = "joinEndDate는 필수값입니다.")
            LocalDate joinEndDate,
            @NotNull(message = "challenges는 필수값입니다.")
            List<UpdateChallenge> challenges
    ) {
        public ChallengeGroupCommand.Update toCommand() {
            return ChallengeGroupCommand.Update.builder()
                    .id(id)
                    .title(title)
                    .content(content)
                    .guide(guide)
                    .category(category)
                    .joinStartDate(joinStartDate)
                    .joinEndDate(joinEndDate)
                    .updateChallenges(challenges.stream().map(UpdateChallenge::toCommand).toList())
                    .build();
        }
    }


    public record UpdateChallenge(
            @NotNull(message = "id는 필수값입니다.")
            Long id,
            @NotNull(message = "activePeriod는 필수값입니다.")
            Integer activePeriod,
            @NotNull(message = "requiredCount는 필수값입니다.")
            Integer requiredCount,
            @NotNull(message = "onceExp는 필수값입니다.")
            Integer onceExp,
            @NotNull(message = "successExp는 필수값입니다.")
            Integer successExp,
            @NotNull(message = "difficulty는 필수값입니다.")
            Integer difficulty
    ){
        public ChallengeGroupCommand.UpdateChallenge toCommand() {
            return ChallengeGroupCommand.UpdateChallenge.builder()
                    .id(id)
                    .activePeriod(activePeriod)
                    .requiredCount(requiredCount)
                    .onceExp(onceExp)
                    .successExp(successExp)
                    .difficulty(difficulty)
                    .build();
        }
    }

}
