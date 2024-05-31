package org.haedal.zzansuni.domain.challengegroup.challengereview;

import java.util.Optional;

public interface ChallengeReviewReader {

    ChallengeReview getByChallengeId(Long challengeId);

    Optional<ChallengeReview> findByUserChallengeId(Long challengeId);

}
