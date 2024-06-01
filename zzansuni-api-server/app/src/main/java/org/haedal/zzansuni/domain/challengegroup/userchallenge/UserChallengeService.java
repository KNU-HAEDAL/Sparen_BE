package org.haedal.zzansuni.domain.challengegroup.userchallenge;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.haedal.zzansuni.domain.challengegroup.challenge.Challenge;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeCommand;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeModel;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeReader;
import org.haedal.zzansuni.domain.challengegroup.review.ChallengeReviewReader;
import org.haedal.zzansuni.domain.user.User;
import org.haedal.zzansuni.domain.user.UserReader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserChallengeService {

    private final UserChallengeStore userChallengeStore;
    private final UserChallengeReader userChallengeReader;
    private final ChallengeReviewReader challengeReviewReader;
    private final UserReader userReader;
    private final ChallengeReader challengeReader;

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
    public ChallengeModel.ChallengeVerificationResult verification(
        Long userChallengeId,
        ChallengeCommand.VerificationCreate command
    ) {
        UserChallenge userChallenge = userChallengeReader.getByIdWithVerificationAndChallenge(
            userChallengeId);
        userChallenge.addChallengeVerification(command);

        // 챌린지 RequiredCount 가져오기 위해 챌린지 정보 가져온다
        Challenge challenge = userChallenge.getChallenge();

        return ChallengeModel.ChallengeVerificationResult
            .of(challenge.getRequiredCount(), userChallenge.getChallengeVerifications().size(),
                challenge.getOnceExp());
    }

    /**
     * 진행중인 챌린지 페이징 조회
     */
    @Transactional(readOnly = true)
    public Page<ChallengeModel.ChallengeCurrent> getCurrentChallenges(Long userId,
        Pageable pageable) {
        Page<UserChallenge> userChallengePage = userChallengeReader
            .getPageByUserId(userId, pageable);

        List<Long> userChallengeIds = userChallengePage.map(UserChallenge::getId).getContent();
        Map<Long, Boolean> reviewWrittenMap = challengeReviewReader
            .getReviewWrittenMapByUserChallengeId(userChallengeIds);

        return userChallengePage.map(
            m -> ChallengeModel.ChallengeCurrent.from(m, reviewWrittenMap.get(m.getId())));
    }
}
