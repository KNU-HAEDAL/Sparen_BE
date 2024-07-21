package org.haedal.zzansuni.domain.challengegroup.challenge.port;

import org.haedal.zzansuni.domain.challengegroup.challenge.Challenge;

import java.util.Optional;

public interface ChallengeReader {

    Challenge getById(Long id);

    Optional<Challenge> findById(Long id);
}
