package org.haedal.zzansuni.controller.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.haedal.zzansuni.domain.challengegroup.ChallengeCategory;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroupCommand;
import org.haedal.zzansuni.domain.challengegroup.DayType;

import java.time.LocalDate;
import java.util.List;

public class AdminReq {
    public record CreateChallengeGroupRequest(
            @NotBlank(message = "title은 필수값입니다.")
            String title,
            @NotBlank(message = "content는 필수값입니다.")
            String content,
            @NotBlank(message = "guide는 필수값입니다.")
            String guide,
            @NotNull(message = "category는 필수값입니다.")
            ChallengeCategory category,
            @NotNull(message = "challenges는 필수값입니다.")
            List<CreateChallengeRequest> challenges
    ){
       public ChallengeGroupCommand.Create toCommand() {
            return ChallengeGroupCommand.Create.builder()
                    .title(title)
                    .content(content)
                    .guide(guide)
                    .category(category)
                    .createChallenges(challenges.stream().map(CreateChallengeRequest::toCommand).toList())
                    .build();
        }
    }

    public record CreateChallengeRequest(
            @NotNull(message = "startDate는 필수값입니다.")
            LocalDate startDate,
            @NotNull(message = "endDate는 필수값입니다.")
            LocalDate endDate,
            @NotNull(message = "dayType은 필수값입니다.")
            DayType dayType,
            @NotNull(message = "requiredCount는 필수값입니다.")
            Integer requiredCount,
            @NotNull(message = "onceExp는 필수값입니다.")
            Integer onceExp,
            @NotNull(message = "successExp는 필수값입니다.")
            Integer successExp,
            @NotNull(message = "difficulty는 필수값입니다.")
            Integer difficulty
    ){
        public ChallengeGroupCommand.CreateChallenge toCommand() {
            return ChallengeGroupCommand.CreateChallenge.builder()
                    .startDate(startDate)
                    .endDate(endDate)
                    .dayType(dayType)
                    .requiredCount(requiredCount)
                    .onceExp(onceExp)
                    .successExp(successExp)
                    .difficulty(difficulty)
                    .build();
        }
    }

}
