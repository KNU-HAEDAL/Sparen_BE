package org.haedal.zzansuni.domain.challengegroup.challengereview;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ChallengeReviewReader {

    ChallengeReview getByChallengeId(Long challengeId);

    Optional<ChallengeReview> findByUserChallengeId(Long challengeId);

    Map<Long, Boolean> getReviewWrittenMapByUserChallengeId(List<Long> userChallengeIds);
}
