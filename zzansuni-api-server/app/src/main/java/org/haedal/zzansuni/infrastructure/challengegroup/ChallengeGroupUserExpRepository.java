package org.haedal.zzansuni.infrastructure.challengegroup;

import org.haedal.zzansuni.domain.challengegroup.userexp.ChallengeGroupUserExp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeGroupUserExpRepository extends JpaRepository<ChallengeGroupUserExp, Long> {
}
