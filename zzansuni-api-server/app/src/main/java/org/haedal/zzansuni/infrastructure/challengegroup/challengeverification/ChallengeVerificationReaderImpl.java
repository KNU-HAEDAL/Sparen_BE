package org.haedal.zzansuni.infrastructure.challengegroup.challengeverification;

import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.challengegroup.challengeverification.ChallengeVerification;
import org.haedal.zzansuni.domain.challengegroup.challengeverification.ChallengeVerificationReader;
import org.haedal.zzansuni.domain.challengegroup.userchallenge.UserChallenge;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChallengeVerificationReaderImpl implements ChallengeVerificationReader {

    private final ChallengeVerificationRepository challengeVerificationRepository;

    @Override
    public ChallengeVerification getById(Long id) {
        return findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Optional<ChallengeVerification> findById(Long id) {
        return challengeVerificationRepository.findById(id);
    }

    /**
     * 사용자 챌린지를 이용하여 챌린지 인증을 몇번 했는지 조회한다.
     */
    @Override
    public Integer countByUserChallengeId(Long userChallengeId) {
        return challengeVerificationRepository.countByUserChallengeId(userChallengeId);
    }

}
