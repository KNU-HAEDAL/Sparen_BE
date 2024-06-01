package org.haedal.zzansuni.controller.challengegroup.review;

import lombok.Builder;
import org.haedal.zzansuni.controller.user.UserRes;

public class ChallengeReviewRes {


    @Builder
    public record ChallengeReviewDto(
            Long challengeId,
            String challengeTitle,
            UserRes.UserDto user,
            String content,
            Integer rating
    ) {

    }

    @Builder
    public record ChallengeReviewWithChalengeDto(
            Long challengeId,
            String challengeTitle,
            Integer challengeDifficulty,
            UserRes.UserDto user,
            String content,
            Integer rating
    ) {

    }
}
