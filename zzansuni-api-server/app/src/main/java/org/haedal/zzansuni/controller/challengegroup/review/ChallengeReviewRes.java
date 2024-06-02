package org.haedal.zzansuni.controller.challengegroup.review;

import java.util.Map;
import lombok.Builder;
import org.haedal.zzansuni.controller.user.UserRes;
import org.haedal.zzansuni.domain.challengegroup.review.ChallengeReviewModel;
import org.haedal.zzansuni.domain.challengegroup.review.ChallengeReviewModel.ChallengeReviewWithChallenge;
import org.haedal.zzansuni.domain.challengegroup.review.ChallengeReviewModel.ChallengeReviewWithUserInfo;

public class ChallengeReviewRes {


    @Builder
    public record ChallengeReviewDto(
        Long challengeId,
        String challengeTitle,
        UserRes.UserDto user,
        String content,
        Integer rating
    ) {

        public static ChallengeReviewDto from(
            ChallengeReviewWithUserInfo challengeReviewWithUserInfo) {
            var user = UserRes.UserDto.from(challengeReviewWithUserInfo.user());
            return ChallengeReviewDto.builder()
                .challengeId(challengeReviewWithUserInfo.challengeId())
                .challengeTitle(challengeReviewWithUserInfo.challengeTitle())
                .user(user)
                .content(challengeReviewWithUserInfo.content())
                .rating(challengeReviewWithUserInfo.rating())
                .build();
        }

    }

    @Builder
    public record ChallengeReviewWithChallengeDto(
        Long challengeId,
        String challengeTitle,
        Integer challengeDifficulty,
        UserRes.UserDto user,
        String content,
        Integer rating
    ) {

        public static ChallengeReviewWithChallengeDto from(
            ChallengeReviewWithChallenge challengeReviewWithChallenge) {
            var user = UserRes.UserDto.from(challengeReviewWithChallenge.user());
            return ChallengeReviewWithChallengeDto.builder()
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
    public record ChallengeReviewScoreResponse(
        Float averageRating,
        /**
         * key: rating, value: count
         */
        Map<Integer, Integer> ratingCount
    ) {

        public static ChallengeReviewScoreResponse from(
            ChallengeReviewModel.ChallengeReviewScore challengeReviewScore) {
            return ChallengeReviewScoreResponse.builder()
                .averageRating(challengeReviewScore.averageRating())
                .ratingCount(challengeReviewScore.ratingCount())
                .build();
        }
    }

}
