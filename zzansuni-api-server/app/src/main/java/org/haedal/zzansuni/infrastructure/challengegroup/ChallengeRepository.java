package org.haedal.zzansuni.infrastructure.challengegroup;

import org.haedal.zzansuni.domain.challengegroup.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

}

