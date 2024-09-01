package org.haedal.zzansuni.userchallenge.infrastructure;

import org.haedal.zzansuni.userchallenge.domain.ChallengeReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChallengeReviewRepository extends JpaRepository<ChallengeReview, Long> {

    Optional<ChallengeReview> findByUserChallengeId(Long userChallengeId);

    List<ChallengeReview> findByUserChallengeIdIn(List<Long> userChallengeIds);
}
