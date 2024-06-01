package org.haedal.zzansuni.infrastructure.challengegroup.challengereview;

import java.util.Optional;
import org.haedal.zzansuni.domain.challengegroup.challengereview.ChallengeReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeReviewRepository extends JpaRepository<ChallengeReview, Long> {

    Optional<ChallengeReview> findByUserChallengeId(Long userChallengeId);
}
