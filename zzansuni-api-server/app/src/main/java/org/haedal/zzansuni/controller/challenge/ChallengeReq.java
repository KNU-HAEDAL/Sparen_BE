package org.haedal.zzansuni.controller.challenge;

import io.swagger.v3.oas.annotations.media.Schema;
import org.haedal.zzansuni.domain.challengegroup.ChallengeCommand;
import org.springframework.web.multipart.MultipartFile;

public class ChallengeReq {

    public record Verification(
        String content
    ) {

        public ChallengeCommand.Verificate toCommand(MultipartFile image) {
            return ChallengeCommand.Verificate.builder()
                .content(content)
                .image(image)
                .build();
        }

    }

    public record ReviewCreate(
        String content,
        @Schema(description = "평점", example = "5")
        Integer rating
    ) {

        public ChallengeCommand.ReviewCreate toCommand() {
            return ChallengeCommand.ReviewCreate.builder()
                .content(content)
                .rating(rating)
                .build();
        }
    }

}
