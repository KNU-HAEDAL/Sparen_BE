package org.haedal.zzansuni.challengereview.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.haedal.zzansuni.core.utils.SelfValidating;

public class ChallengeReviewCommand {
    @Getter
    public static class Create extends SelfValidating<Create> {

        @NotBlank(message = "내용은 필수입니다.")
        private final String content;

        @NotNull(message = "평점은 필수입니다.")
        @Min(1)
        @Max(5)
        private final Integer rating;

        @NotNull(message = "평점은 필수입니다.")
        @Min(1)
        @Max(5)
        private final Integer difficulty;

        @NotNull(message = "평점은 필수입니다.")
        @Min(1)
        @Max(3)
        private final Integer achievement;

        @Builder
        public Create(String content, Integer rating, Integer difficulty, Integer achievement) {
            this.content = content;
            this.rating = rating;
            this.difficulty = difficulty;
            this.achievement = achievement;
            this.validateSelf();
        }


    }
}
