package org.haedal.zzansuni.controller.challengegroup.challenge;

public class ChallengeReq {

    public record ChallengeVerificationRequest(
            String content
    ) {
    }

    public record ChallengeReviewCreateRequest(
            String content,
            Integer rating
    ) {
    }
}
