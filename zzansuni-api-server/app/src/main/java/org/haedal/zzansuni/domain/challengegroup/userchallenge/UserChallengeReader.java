package org.haedal.zzansuni.domain.challengegroup.userchallenge;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserChallengeReader {

    UserChallenge getById(Long id);

    UserChallenge getByIdWithVerificationAndChallenge(Long id);

    Optional<UserChallenge> findById(Long id);

    UserChallenge getByUserIdAndChallengeId(Long userId, Long challengeId);

    Optional<UserChallenge> findByUserIdAndChallengeId(Long userId, Long challengeId);

    Page<UserChallenge> getCurrentChallengePageByUserId(Long userId, Pageable pageable);

    Page<UserChallenge> getCompletedChallengePageByUserId(Long userId, Pageable pageable);
}
