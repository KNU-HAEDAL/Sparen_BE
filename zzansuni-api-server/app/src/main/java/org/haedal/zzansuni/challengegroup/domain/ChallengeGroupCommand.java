package org.haedal.zzansuni.challengegroup.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.haedal.zzansuni.core.utils.SelfValidating;

import java.time.LocalDate;
import java.util.List;

public class ChallengeGroupCommand {
    @Getter
    public static class Create extends SelfValidating<Create> {
        @NotBlank(message = "title은 필수값입니다.")
        private final String title;
        @NotBlank(message = "content는 필수값입니다.")
        private final String content;
        @NotBlank(message = "guide는 필수값입니다.")
        private final String guide;
        @NotNull(message = "category는 필수값입니다.")
        private final ChallengeCategory category;
        @NotNull(message = "joinStartDate는 필수값입니다.")
        private final LocalDate joinStartDate;
        @NotNull(message = "joinEndDate는 필수값입니다.")
        private final LocalDate joinEndDate;
        @NotNull(message = "challenges는 필수값입니다.")
        private final List<CreateChallenge> createChallenges;

        @Builder
        public Create(String title, String content, String guide, ChallengeCategory category, LocalDate joinStartDate, LocalDate joinEndDate, List<CreateChallenge> createChallenges) {
            this.title = title;
            this.content = content;
            this.guide = guide;
            this.category = category;
            this.joinStartDate = joinStartDate;
            this.joinEndDate = joinEndDate;
            this.createChallenges = createChallenges;
            this.validateSelf();
        }
    }

    @Getter
    public static class CreateChallenge extends SelfValidating<CreateChallenge> {
        @NotNull(message = "requiredCount는 필수값입니다.")
        private final Integer requiredCount;
        @NotNull(message = "onceExp는 필수값입니다.")
        private final Integer onceExp;
        @NotNull(message = "successExp는 필수값입니다.")
        private final Integer successExp;
        @NotNull(message = "difficulty는 필수값입니다.")
        private final Integer difficulty;
        @NotNull(message = "activePeriod는 필수값입니다.")
        private final Integer activePeriod;

        @Builder
        public CreateChallenge(Integer requiredCount, Integer onceExp, Integer successExp, Integer difficulty, Integer activePeriod) {
            this.requiredCount = requiredCount;
            this.onceExp = onceExp;
            this.successExp = successExp;
            this.difficulty = difficulty;
            this.activePeriod = activePeriod;
            this.validateSelf();
        }
    }



    @Getter
    public static class Update extends SelfValidating<Update> {
        @NotNull(message = "id는 필수값입니다.")
        private final Long id;
        @NotBlank(message = "title은 필수값입니다.")
        private final String title;
        @NotBlank(message = "content는 필수값입니다.")
        private final String content;
        @NotBlank(message = "guide는 필수값입니다.")
        private final String guide;
        @NotNull(message = "category는 필수값입니다.")
        private final ChallengeCategory category;
        @NotNull(message = "joinStartDate는 필수값입니다.")
        private final LocalDate joinStartDate;
        @NotNull(message = "joinEndDate는 필수값입니다.")
        private final LocalDate joinEndDate;
        private final List<UpdateChallenge> updateChallenges;
        private final List<CreateChallenge> createChallenges;

        @Builder
        public Update(Long id, String title, String content, String guide, ChallengeCategory category, LocalDate joinStartDate, LocalDate joinEndDate, List<UpdateChallenge> updateChallenges, List<CreateChallenge> createChallenges) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.guide = guide;
            this.category = category;
            this.joinStartDate = joinStartDate;
            this.joinEndDate = joinEndDate;
            this.updateChallenges = updateChallenges;
            this.createChallenges = createChallenges;
            this.validateSelf();
        }
    }

    @Getter
    public static class UpdateChallenge extends SelfValidating<UpdateChallenge> {
        @NotNull(message = "id는 필수값입니다.")
        private final Long id;
        @NotNull(message = "requiredCount는 필수값입니다.")
        private final Integer requiredCount;
        @NotNull(message = "onceExp는 필수값입니다.")
        private final Integer onceExp;
        @NotNull(message = "successExp는 필수값입니다.")
        private final Integer successExp;
        @NotNull(message = "difficulty는 필수값입니다.")
        private final Integer difficulty;
        @NotNull(message = "activePeriod는 필수값입니다.")
        private final Integer activePeriod;

        @Builder
        public UpdateChallenge(Long id, Integer requiredCount, Integer onceExp, Integer successExp, Integer difficulty, Integer activePeriod) {
            this.id = id;
            this.requiredCount = requiredCount;
            this.onceExp = onceExp;
            this.successExp = successExp;
            this.difficulty = difficulty;
            this.activePeriod = activePeriod;
            this.validateSelf();
        }

        public ChallengeGroupCommand.CreateChallenge convertCreate() {
            return ChallengeGroupCommand.CreateChallenge.builder()
                    .requiredCount(requiredCount)
                    .onceExp(onceExp)
                    .successExp(successExp)
                    .difficulty(difficulty)
                    .activePeriod(activePeriod)
                    .build();
        }
    }
}
