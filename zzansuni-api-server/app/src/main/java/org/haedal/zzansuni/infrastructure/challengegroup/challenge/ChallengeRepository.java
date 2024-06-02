package org.haedal.zzansuni.infrastructure.challengegroup.challenge;

import org.haedal.zzansuni.domain.challengegroup.challenge.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

}

