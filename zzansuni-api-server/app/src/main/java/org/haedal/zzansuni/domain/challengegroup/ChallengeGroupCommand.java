package org.haedal.zzansuni.domain.challengegroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class ChallengeGroupCommand {
    @Builder
    @Getter
    public static class Create {
        @NotBlank(message = "title은 필수값입니다.")
        private final String title;
        @NotBlank(message = "content는 필수값입니다.")
        private final String content;
        @NotBlank(message = "guide는 필수값입니다.")
        private final String guide;
        @NotNull(message = "category는 필수값입니다.")
        private final ChallengeCategory category;
        @NotNull(message = "challenges는 필수값입니다.")
        private final List<CreateChallenge> createChallenges;

    }

    @Builder
    @Getter
    public static class CreateChallenge {
        @NotNull(message = "startDate는 필수값입니다.")
        private final LocalDate startDate;
        @NotNull(message = "endDate는 필수값입니다.")
        private final LocalDate endDate;
        @NotNull(message = "dayType은 필수값입니다.")
        private final DayType dayType;
        @NotNull(message = "requiredCount는 필수값입니다.")
        private final Integer requiredCount;
        @NotNull(message = "onceExp는 필수값입니다.")
        private final Integer onceExp;
        @NotNull(message = "successExp는 필수값입니다.")
        private final Integer successExp;
        @NotNull(message = "difficulty는 필수값입니다.")
        private final Integer difficulty;
    }
}
