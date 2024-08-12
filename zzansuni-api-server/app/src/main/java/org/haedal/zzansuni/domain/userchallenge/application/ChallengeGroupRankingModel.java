package org.haedal.zzansuni.domain.userchallenge.application;

import lombok.Builder;
import org.haedal.zzansuni.domain.user.UserModel;
import org.haedal.zzansuni.domain.userchallenge.ChallengeGroupUserExp;

public class ChallengeGroupRankingModel {
    @Builder
    public record Main(
            UserModel.Main user,
            Integer rank,
            Integer accumulatedPoint
    ) {
        public static Main from(ChallengeGroupUserExp challengeGroupUserExp, int rank) {
            var userModel = UserModel.Main.from(challengeGroupUserExp.getUser());
            return Main.builder()
                    .user(userModel)
                    .rank(rank)
                    .accumulatedPoint(challengeGroupUserExp.getTotalExp())
                    .build();
        }
    }
}
