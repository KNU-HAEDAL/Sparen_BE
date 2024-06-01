package org.haedal.zzansuni.domain.challengegroup.review;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChallengeReviewReader {

    ChallengeReview getByChallengeId(Long challengeId);

    Optional<ChallengeReview> findByUserChallengeId(Long challengeId);

    Map<Long, Boolean> getReviewWrittenMapByUserChallengeId(List<Long> userChallengeIds);

    Page<ChallengeReview> getChallengeReviewPageByChallengeGroupId(Long challengeGroupId,
        Pageable pageable);

}
