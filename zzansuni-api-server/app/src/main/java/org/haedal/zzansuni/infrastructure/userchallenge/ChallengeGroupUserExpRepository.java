package org.haedal.zzansuni.infrastructure.userchallenge;

import org.haedal.zzansuni.domain.userchallenge.userexp.ChallengeGroupUserExp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeGroupUserExpRepository extends JpaRepository<ChallengeGroupUserExp, Long> {
}
