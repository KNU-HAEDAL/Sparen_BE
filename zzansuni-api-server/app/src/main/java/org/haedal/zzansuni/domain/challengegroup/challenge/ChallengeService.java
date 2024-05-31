package org.haedal.zzansuni.domain.challengegroup.challenge;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeCommand.Verificate;
import org.haedal.zzansuni.domain.challengegroup.challengeverification.ChallengeVerification;
import org.haedal.zzansuni.domain.challengegroup.challengeverification.ChallengeVerificationStore;
import org.haedal.zzansuni.domain.challengegroup.userchallenge.UserChallenge;
import org.haedal.zzansuni.domain.challengegroup.userchallenge.UserChallengeReader;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChallengeService {

    private final ChallengeReader challengeReader;
    private final ChallengeStore challengeStore;

    private final UserChallengeReader userChallengeReader;

    private final ChallengeVerificationStore challengeVerificationStore;

    /**
     * 챌린지 인증하기
     */
    public void verification(Long userChallengeId, Verificate verificate) {
        UserChallenge userChallenge = userChallengeReader.getById(userChallengeId);
        ChallengeVerification challengeVerification = ChallengeVerification.create(verificate,
            userChallenge);
        challengeVerificationStore.store(challengeVerification);
    }
}
