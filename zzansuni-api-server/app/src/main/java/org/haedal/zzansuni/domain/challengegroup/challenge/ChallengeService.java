package org.haedal.zzansuni.domain.challengegroup.challenge;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeCommand.Verificate;
import org.haedal.zzansuni.domain.challengegroup.challengeverification.ChallengeVerification;
import org.haedal.zzansuni.domain.challengegroup.challengeverification.ChallengeVerificationReader;
import org.haedal.zzansuni.domain.challengegroup.challengeverification.ChallengeVerificationStore;
import org.haedal.zzansuni.domain.challengegroup.userchallenge.UserChallenge;
import org.haedal.zzansuni.domain.challengegroup.userchallenge.UserChallengeReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class ChallengeService {

    private final ChallengeReader challengeReader;
    private final ChallengeStore challengeStore;

    private final UserChallengeReader userChallengeReader;

    private final ChallengeVerificationStore challengeVerificationStore;
    private final ChallengeVerificationReader challengeVerificationReader;

    /**
     * 챌린지 인증하기 1. ChallengeVerification 테이블에 데이터 추가 2. Challenge 엔티티에서 필요참여횟수 가져오기 3. UserChallenge
     * 엔티티에서 참여횟수 가져오기 4. 참여횟수와 필요참여횟수가 같으면 챌린지 완료로 변경
     */
    public ChallengeModel.ChallengeVerificationResult verification(Long userChallengeId,
        Verificate verificate) {
        UserChallenge userChallenge = userChallengeReader.getById(userChallengeId);
        ChallengeVerification challengeVerification = ChallengeVerification.create(verificate,
            userChallenge);
        challengeVerificationStore.store(challengeVerification);

        Challenge challenge = userChallenge.getChallenge();

        /**
         * 사용자가 챌린지를 몇번 수행했는지 가져온다
         */
        Integer currentCount = challengeVerificationReader.countByUserChallengeId(userChallengeId);
        log.info("currentCount: {}", currentCount);
        /**
         * 참여횟수와 필요참여횟수가 같으면 챌린지 완료로 변경
         */
        userChallenge.verification(currentCount, challenge.getRequiredCount());

        return ChallengeModel.ChallengeVerificationResult
            .from(challenge.getRequiredCount(), currentCount, challenge.getOnceExp());
    }
}
