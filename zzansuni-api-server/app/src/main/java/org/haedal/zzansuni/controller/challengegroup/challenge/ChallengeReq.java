package org.haedal.zzansuni.controller.challengegroup.challenge;

import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeCommand;
import org.springframework.web.multipart.MultipartFile;

public class ChallengeReq {

    public record ChallengeVerificationRequest(
        String content
    ) {

        public ChallengeCommand.Verificate toCommand(MultipartFile image) {
            return ChallengeCommand.Verificate.builder()
                .content(content)
                .image(image)
                .build();
        }

    }

    public record ChallengeReviewCreateRequest(
        String content,
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
