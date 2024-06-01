package org.haedal.zzansuni.infrastructure.challengegroup;

import org.haedal.zzansuni.domain.challengegroup.ChallengeGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeGroupRepository extends JpaRepository<ChallengeGroup, Long> {
}
