package org.haedal.zzansuni.domain.challengegroup.challengeverification;

import java.util.Optional;

public interface ChallengeVerificationReader {

    ChallengeVerification getById(Long id);

    Optional<ChallengeVerification> findById(Long id);
}
