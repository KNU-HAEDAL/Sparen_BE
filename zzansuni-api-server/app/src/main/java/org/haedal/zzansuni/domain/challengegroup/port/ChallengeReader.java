package org.haedal.zzansuni.domain.challengegroup.port;

import org.haedal.zzansuni.domain.challengegroup.Challenge;

import java.util.Optional;

public interface ChallengeReader {

    Challenge getById(Long id);

    Optional<Challenge> findById(Long id);
}
