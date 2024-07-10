package org.haedal.zzansuni.domain.challengegroup.review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import org.haedal.zzansuni.domain.user.UserModel;

public class ChallengeReviewModel {

    @Builder
    public record ChallengeReviewWithUserInfo(
        Long challengeId,
        String challengeTitle,
        UserModel.Main user,
        String content,
        Integer rating
    ) {

        public static ChallengeReviewWithUserInfo from(ChallengeReview challengeReview) {
            var userModel = UserModel.Main.from(challengeReview.getUserChallenge().getUser());
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
        UserModel.Main user,
        String content,
        Integer rating
    ) {

        public static ChallengeReviewWithChallenge from(ChallengeReview challengeReview) {
            var userModel = UserModel.Main.from(challengeReview.getUserChallenge().getUser());
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

    @Builder
    public record ChallengeReviewScore(
        Float averageRating,
        Map<Integer, Integer> ratingCount
    ) {

        public static ChallengeReviewScore of(List<ChallengeReview> challengeReviews) {
            // key: rating, value: count
            // rating은 1,2,3,4,5 이며 value는 각각의 rating이 몇개인지 count
            Map<Integer, Integer> ratingCount = new HashMap<>();
            for (int i = 1; i <= 5; i++) {
                ratingCount.put(i, 0);
            }
            challengeReviews.forEach(challengeReview -> {
                var rating = challengeReview.getRating();
                ratingCount.put(rating, ratingCount.get(rating) + 1);
            });

            var averageRating = challengeReviews.stream()
                .mapToInt(ChallengeReview::getRating)
                .average()
                .orElse(0);
            return ChallengeReviewScore.builder()
                .averageRating((float) averageRating)
                .ratingCount(ratingCount)
                .build();
        }
    }

}
