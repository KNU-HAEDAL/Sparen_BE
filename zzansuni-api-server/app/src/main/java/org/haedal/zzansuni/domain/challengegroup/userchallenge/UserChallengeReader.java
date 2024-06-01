package org.haedal.zzansuni.domain.challengegroup.userchallenge;

import java.util.Optional;

public interface UserChallengeReader {

    UserChallenge getById(Long id);
    UserChallenge getByIdWithVerificationAndChallenge(Long id);

    Optional<UserChallenge> findById(Long id);

    UserChallenge getByUserIdAndChallengeId(Long userId, Long challengeId);

    Optional<UserChallenge> findByUserIdAndChallengeId(Long userId, Long challengeId);
}
