package org.haedal.zzansuni.controller.challengegroup.review;

import java.util.Map;
import lombok.Builder;
import org.haedal.zzansuni.controller.user.UserRes;
import org.haedal.zzansuni.domain.challengegroup.review.ChallengeReviewModel;
import org.haedal.zzansuni.domain.challengegroup.review.ChallengeReviewModel.ChallengeReviewWithChallenge;
import org.haedal.zzansuni.domain.challengegroup.review.ChallengeReviewModel.ChallengeReviewWithUserInfo;

public class ChallengeReviewRes {


    @Builder
    public record Info(
        Long challengeId,
        String challengeTitle,
        UserRes.User user,
        String content,
        Integer rating
    ) {

        public static Info from(
            ChallengeReviewWithUserInfo challengeReviewWithUserInfo) {
            var user = UserRes.User.from(challengeReviewWithUserInfo.user());
            return Info.builder()
                .challengeId(challengeReviewWithUserInfo.challengeId())
                .challengeTitle(challengeReviewWithUserInfo.challengeTitle())
                .user(user)
                .content(challengeReviewWithUserInfo.content())
                .rating(challengeReviewWithUserInfo.rating())
                .build();
        }

    }

    @Builder
    public record InfoWithChallenge(
        Long challengeId,
        String challengeTitle,
        Integer challengeDifficulty,
        UserRes.User user,
        String content,
        Integer rating
    ) {

        public static InfoWithChallenge from(
            ChallengeReviewWithChallenge challengeReviewWithChallenge) {
            var user = UserRes.User.from(challengeReviewWithChallenge.user());
            return InfoWithChallenge.builder()
                .challengeId(challengeReviewWithChallenge.challengeId())
                .challengeTitle(challengeReviewWithChallenge.challengeTitle())
                .challengeDifficulty(challengeReviewWithChallenge.challengeDifficulty())
                .user(user)
                .content(challengeReviewWithChallenge.content())
                .rating(challengeReviewWithChallenge.rating())
                .build();
        }
    }

    @Builder
    public record ScoreResponse(
        Float averageRating,
        /**
         * key: rating, value: count
         */
        Map<Integer, Integer> ratingCount
    ) {

        public static ScoreResponse from(
            ChallengeReviewModel.ChallengeReviewScore challengeReviewScore) {
            return ScoreResponse.builder()
                .averageRating(challengeReviewScore.averageRating())
                .ratingCount(challengeReviewScore.ratingCount())
                .build();
        }
    }

}
