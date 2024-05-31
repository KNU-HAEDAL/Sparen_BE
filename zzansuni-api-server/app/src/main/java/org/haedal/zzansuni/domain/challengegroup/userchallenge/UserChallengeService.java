package org.haedal.zzansuni.domain.challengegroup.userchallenge;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.challengegroup.challenge.Challenge;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeReader;
import org.haedal.zzansuni.domain.user.User;
import org.haedal.zzansuni.domain.user.UserReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserChallengeService {

    private final UserChallengeStore userChallengeStore;
    private final UserChallengeReader userChallengeReader;
    private final UserReader userReader;
    private final ChallengeReader challengeReader;

    /**
     * 챌린지 참여하기 1. 유저와 챌린지 정보를 받아서 UserChallenge 테이블에 데이터 추가
     */
    public void participateChallenge(Long userId,
        UserChallengeCommand.Participate participateInfo) {
        User user = userReader.getById(userId);
        Challenge challenge = challengeReader.getById(participateInfo.getChallengeId());

        /**
         * 이미 참여한 챌린지인지 확인
         */
        userChallengeReader.findByUserIdAndChallengeId(userId, participateInfo.getChallengeId())
            .ifPresent(userChallenge -> {
                throw new IllegalArgumentException("이미 참여한 챌린지입니다.");
            });
        
        UserChallenge userChallenge = UserChallenge.from(challenge, user);
        userChallengeStore.store(userChallenge);

        // TODO: 챌린지 그룹 누적 참여자수 증가 로직 추가
    }

}
