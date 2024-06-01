package org.haedal.zzansuni.infrastructure.challengegroup.challengeverification;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.challengegroup.challengeverification.ChallengeVerification;
import org.haedal.zzansuni.domain.challengegroup.challengeverification.ChallengeVerificationStore;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChallengeVerificationStoreImpl implements ChallengeVerificationStore {

    private final ChallengeVerificationRepository challengeVerificationRepository;

    @Override
    public ChallengeVerification store(ChallengeVerification challengeVerification) {
        return challengeVerificationRepository.save(challengeVerification);
    }
}
