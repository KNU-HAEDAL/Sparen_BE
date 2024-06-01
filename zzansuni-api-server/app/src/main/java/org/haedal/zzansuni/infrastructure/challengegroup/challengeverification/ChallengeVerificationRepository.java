package org.haedal.zzansuni.infrastructure.challengegroup.challengeverification;

import java.util.List;
import org.haedal.zzansuni.domain.challengegroup.challengeverification.ChallengeVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeVerificationRepository extends
    JpaRepository<ChallengeVerification, Long> {

    /**
     * 사용자 챌린지를 이용하여 챌린지 인증을 몇번 했는지 조회한다.
     *
     * @param userChallengeId
     * @return Integer
     */
    Integer countByUserChallengeId(Long userChallengeId);

    List<ChallengeVerification> findByUserChallengeId(Long id);

}
