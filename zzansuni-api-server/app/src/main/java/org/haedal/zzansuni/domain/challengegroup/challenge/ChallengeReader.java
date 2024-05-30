package org.haedal.zzansuni.domain.challengegroup.challenge;

import java.util.Optional;

public interface ChallengeReader {

    Challenge getById(Long id);

    Optional<Challenge> findById(Long id);
}
