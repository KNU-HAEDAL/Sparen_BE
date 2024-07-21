package org.haedal.zzansuni.domain.userchallenge.verification.port;

import org.haedal.zzansuni.domain.userchallenge.verification.ChallengeVerification;

import java.util.List;
import java.util.Optional;

public interface ChallengeVerificationReader {

    ChallengeVerification getById(Long id);

    Optional<ChallengeVerification> findById(Long id);

    /**
     * 사용자 챌린지를 이용하여 챌린지 인증을 몇번 했는지 조회한다.
     */
    Integer countByUserChallengeId(Long userChallengeId);

    List<ChallengeVerification> findByUserChallengeId(Long id);

}
