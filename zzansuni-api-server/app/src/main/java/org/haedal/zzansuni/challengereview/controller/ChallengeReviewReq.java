package org.haedal.zzansuni.challengereview.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import org.haedal.zzansuni.challengereview.domain.ChallengeReviewCommand;

public class ChallengeReviewReq {
    public record Create(
            String content,
            @Schema(description = "평점", example = "5")
            Integer rating,
            @Schema(description = "체감 난이도", example = "1")
            Integer difficulty,
            @Schema(description = "성취도", example = "1")
            Integer achievement
    ) {

        public ChallengeReviewCommand.Create toCommand() {
            return ChallengeReviewCommand.Create.builder()
                    .content(content)
                    .rating(rating)
                    .difficulty(difficulty)
                    .achievement(achievement)
                    .build();
        }
    }
}
