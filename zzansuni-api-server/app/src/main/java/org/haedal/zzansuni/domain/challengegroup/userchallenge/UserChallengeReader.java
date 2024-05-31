package org.haedal.zzansuni.domain.challengegroup.userchallenge;

import java.util.Optional;

public interface UserChallengeReader {

    UserChallenge getById(Long id);

    Optional<UserChallenge> findById(Long id);
}
