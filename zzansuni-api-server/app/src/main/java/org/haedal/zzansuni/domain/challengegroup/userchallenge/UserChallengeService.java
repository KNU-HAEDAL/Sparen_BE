package org.haedal.zzansuni.domain.challengegroup.userchallenge;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.haedal.zzansuni.domain.challengegroup.challenge.Challenge;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeCommand.Verificate;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeModel;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeReader;
import org.haedal.zzansuni.domain.challengegroup.challengeverification.ChallengeVerification;
import org.haedal.zzansuni.domain.challengegroup.challengeverification.ChallengeVerificationReader;
import org.haedal.zzansuni.domain.challengegroup.challengeverification.ChallengeVerificationStore;
import org.haedal.zzansuni.domain.user.User;
import org.haedal.zzansuni.domain.user.UserReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserChallengeService {

    private final UserChallengeStore userChallengeStore;
    private final UserChallengeReader userChallengeReader;
    private final UserReader userReader;
    private final ChallengeReader challengeReader;

    private final ChallengeVerificationStore challengeVerificationStore;
    private final ChallengeVerificationReader challengeVerificationReader;

    /**
     * 챌린지 참여하기 1. 유저와 챌린지 정보를 받아서 UserChallenge 테이블에 데이터 추가
     */
    @Transactional
    public void participateChallenge(Long userId, Long challengeId) {
        User user = userReader.getById(userId);
        Challenge challenge = challengeReader.getById(challengeId);

        /**
         * 이미 참여한 챌린지인지 확인
         */
        userChallengeReader.findByUserIdAndChallengeId(userId, challengeId)
            .ifPresent(userChallenge -> {
                throw new IllegalArgumentException("이미 참여한 챌린지입니다.");
            });

        UserChallenge userChallenge = UserChallenge.create(challenge, user);
        userChallengeStore.store(userChallenge);

        // TODO: 챌린지 그룹 누적 참여자수 증가 로직 추가
    }

    /**
     * 챌린지 인증하기 <p> 1. ChallengeVerification 테이블에 데이터 추가 <br> 2. Challenge 엔티티에서 필요참여횟수 가져오기 <br> 3.
     * UserChallenge 엔티티에서 참여횟수 가져오기 <br> 4. 참여횟수와 필요참여횟수가 같으면 챌린지 완료로 변경
     */
    @Transactional
    public ChallengeModel.ChallengeVerificationResult verification(Long userChallengeId,
        Verificate verificate) {
        UserChallenge userChallenge = userChallengeReader.getById(userChallengeId);
        // TODO 이미지 업로드 로직 필요
        ChallengeVerification challengeVerification = ChallengeVerification.create(verificate,
            userChallenge);
        challengeVerificationStore.store(challengeVerification);

        // 챌린지 RequiredCount 가져오기 위해 챌린지 정보 가져온다

        Challenge challenge = userChallenge.getChallenge();

        //사용자가 챌린지를 몇번 수행했는지 가져온다
        Integer currentCount = challengeVerificationReader.countByUserChallengeId(userChallengeId);
        log.info("currentCount: {}", currentCount);

        //참여횟수와 필요참여횟수가 같으면 챌린지 완료로 변경
        userChallenge.tryComplete(currentCount, challenge.getRequiredCount());

        return ChallengeModel.ChallengeVerificationResult
            .of(challenge.getRequiredCount(), currentCount, challenge.getOnceExp());
    }
}
