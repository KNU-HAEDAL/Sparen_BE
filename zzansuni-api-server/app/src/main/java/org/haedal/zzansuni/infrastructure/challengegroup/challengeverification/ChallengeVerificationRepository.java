package org.haedal.zzansuni.infrastructure.challengegroup.challengeverification;

import org.haedal.zzansuni.domain.challengegroup.challengeverification.ChallengeVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeVerificationRepository extends
    JpaRepository<ChallengeVerification, Long> {

}
