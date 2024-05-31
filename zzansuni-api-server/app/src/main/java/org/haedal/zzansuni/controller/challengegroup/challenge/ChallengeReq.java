package org.haedal.zzansuni.controller.challengegroup.challenge;

import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeCommand;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeModel;

public class ChallengeReq {

    public record ChallengeVerificationRequest(
        String content
    ) {

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
