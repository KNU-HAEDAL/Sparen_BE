package org.haedal.zzansuni.challengereview.infrastructure;

import org.haedal.zzansuni.challengereview.domain.ChallengeReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChallengeReviewRepository extends JpaRepository<ChallengeReview, Long> {

    Optional<ChallengeReview> findByUserChallengeId(Long userChallengeId);

    List<ChallengeReview> findByUserChallengeIdIn(List<Long> userChallengeIds);
}
