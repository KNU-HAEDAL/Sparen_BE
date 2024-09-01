package org.haedal.zzansuni.challengegroup.infrastructure;

import org.haedal.zzansuni.challengegroup.domain.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

}

