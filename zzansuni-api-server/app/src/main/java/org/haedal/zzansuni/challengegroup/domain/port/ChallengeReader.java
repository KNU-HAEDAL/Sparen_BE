package org.haedal.zzansuni.challengegroup.domain.port;

import org.haedal.zzansuni.challengegroup.domain.Challenge;

import java.util.Optional;

public interface ChallengeReader {

    Challenge getById(Long id);

    Optional<Challenge> findById(Long id);
}
