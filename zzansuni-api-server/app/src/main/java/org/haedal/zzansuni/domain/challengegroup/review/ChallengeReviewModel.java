package org.haedal.zzansuni.domain.challengegroup.review;

import lombok.Builder;
import org.haedal.zzansuni.domain.user.UserModel;

public class ChallengeReviewModel {

    @Builder
    public record ChallengeReviewWithUserInfo(
        Long challengeId,
        String challengeTitle,
        UserModel user,
        String content,
        Integer rating
    ) {

        public static ChallengeReviewWithUserInfo from(ChallengeReview challengeReview) {
            var userModel = UserModel.from(challengeReview.getUserChallenge().getUser());
            return ChallengeReviewWithUserInfo.builder()
                .challengeId(challengeReview.getUserChallenge().getChallenge().getId())
                .challengeTitle(
                    challengeReview.getUserChallenge().getChallenge().getChallengeGroup()
                        .getTitle())
                .user(userModel)
                .content(challengeReview.getContent())
                .rating(challengeReview.getRating())
                .build();
        }
    }

    @Builder
    public record ChallengeReviewWithChallenge(
        Long challengeId,
        String challengeTitle,
        Integer challengeDifficulty,
        UserModel user,
        String content,
        Integer rating
    ) {

        public static ChallengeReviewWithChallenge from(ChallengeReview challengeReview) {
            var userModel = UserModel.from(challengeReview.getUserChallenge().getUser());
            return ChallengeReviewWithChallenge.builder()
                .challengeId(challengeReview.getUserChallenge().getChallenge().getId())
                .challengeTitle(
                    challengeReview.getUserChallenge().getChallenge().getChallengeGroup()
                        .getTitle())
                .challengeDifficulty(
                    challengeReview.getUserChallenge().getChallenge().getDifficulty())
                .user(userModel)
                .content(challengeReview.getContent())
                .rating(challengeReview.getRating())
                .build();
        }

    }

}
