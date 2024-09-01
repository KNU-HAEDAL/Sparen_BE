package org.haedal.zzansuni.userchallenge.domain.port;

import org.haedal.zzansuni.userchallenge.domain.ChallengeReview;

public interface ChallengeReviewStore {

    ChallengeReview store(ChallengeReview challengeReview);

}
