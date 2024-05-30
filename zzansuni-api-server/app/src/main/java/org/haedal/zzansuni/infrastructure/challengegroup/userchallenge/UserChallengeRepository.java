package org.haedal.zzansuni.infrastructure.challengegroup.userchallenge;

import org.haedal.zzansuni.domain.challengegroup.userchallenge.UserChallenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserChallengeRepository extends JpaRepository<UserChallenge, Long> {

}
