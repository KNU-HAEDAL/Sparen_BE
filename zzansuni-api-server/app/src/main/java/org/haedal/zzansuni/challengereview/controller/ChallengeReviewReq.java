package org.haedal.zzansuni.challengereview.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import org.haedal.zzansuni.challengereview.domain.ChallengeReviewCommand;

public class ChallengeReviewReq {
    public record Create(
            String content,
            @Schema(description = "평점", example = "5")
            Integer rating
    ) {

        public ChallengeReviewCommand.Create toCommand() {
            return ChallengeReviewCommand.Create.builder()
                    .content(content)
                    .rating(rating)
                    .build();
        }
    }
}
