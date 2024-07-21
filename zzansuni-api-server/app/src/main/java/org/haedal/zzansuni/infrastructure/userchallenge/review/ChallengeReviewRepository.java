package org.haedal.zzansuni.infrastructure.userchallenge.review;

import java.util.List;
import java.util.Optional;
import org.haedal.zzansuni.domain.userchallenge.review.ChallengeReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeReviewRepository extends JpaRepository<ChallengeReview, Long> {

    Optional<ChallengeReview> findByUserChallengeId(Long userChallengeId);

    List<ChallengeReview> findByUserChallengeIdIn(List<Long> userChallengeIds);
}
