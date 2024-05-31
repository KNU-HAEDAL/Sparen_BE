package org.haedal.zzansuni.infrastructure.challengegroup.userchallenge;

import java.util.Optional;
import org.haedal.zzansuni.domain.challengegroup.userchallenge.UserChallenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserChallengeRepository extends JpaRepository<UserChallenge, Long> {

    Optional<UserChallenge> findByUserIdAndChallengeId(Long userId, Long challengeId);

}
