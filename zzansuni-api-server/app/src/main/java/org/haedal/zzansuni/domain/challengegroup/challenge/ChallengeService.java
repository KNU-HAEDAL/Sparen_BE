package org.haedal.zzansuni.domain.challengegroup.challenge;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroup;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeModel.ChallengeRecord;
import org.haedal.zzansuni.domain.challengegroup.challengereview.ChallengeReview;
import org.haedal.zzansuni.domain.challengegroup.challengereview.ChallengeReviewReader;
import org.haedal.zzansuni.domain.challengegroup.challengereview.ChallengeReviewStore;
import org.haedal.zzansuni.domain.challengegroup.challengeverification.ChallengeVerification;
import org.haedal.zzansuni.domain.challengegroup.challengeverification.ChallengeVerificationModel;
import org.haedal.zzansuni.domain.challengegroup.challengeverification.ChallengeVerificationReader;
import org.haedal.zzansuni.domain.challengegroup.userchallenge.UserChallenge;
import org.haedal.zzansuni.domain.challengegroup.userchallenge.UserChallengeReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChallengeService {

    private final ChallengeReader challengeReader;

    private final UserChallengeReader userChallengeReader;

    private final ChallengeVerificationReader challengeVerificationReader;

    private final ChallengeReviewStore challengeReviewStore;
    private final ChallengeReviewReader challengeReviewReader;


    /**
     * 챌린지 기록 가져오기
     */

    @Transactional(readOnly = true)
    public ChallengeRecord getChallengeRecord(Long userId, Long challengeId) {
        Challenge challenge = challengeReader.getById(challengeId);
        ChallengeGroup challengeGroup = challenge.getChallengeGroup();
        UserChallenge userChallenge = userChallengeReader.getByUserIdAndChallengeId(userId,
            challengeId);
        List<ChallengeVerification> challengeVerifications = challengeVerificationReader.findByUserChallengeId(
            userChallenge.getId());
        return ChallengeRecord.from(challenge, challengeGroup, challengeVerifications);

    }

    /**
     * 챌린지 기록 상세 가져오기
     */

    @Transactional(readOnly = true)
    public ChallengeVerificationModel getChallengeRecordDetail(Long recordId) {
        ChallengeVerification challengeVerification = challengeVerificationReader.getById(recordId);
        return ChallengeVerificationModel.from(challengeVerification);
    }

    /**
     * 챌린지 리뷰 작성하기
     */
    @Transactional
    public Long createReview(ChallengeCommand.ReviewCreate command, Long challengeId, Long userId) {
        UserChallenge userChallenge = userChallengeReader.findByUserIdAndChallengeId(userId,
            challengeId).orElseThrow(() -> new IllegalArgumentException("현재 참여중인 챌린지가 아닙니다."));

        //이미 리뷰를 작성했는지 확인
        challengeReviewReader.findByUserChallengeId(challengeId)
            .ifPresent(review -> {
                throw new IllegalArgumentException("이미 리뷰를 작성했습니다.");
            });
        ChallengeReview challengeReview = ChallengeReview.create(userChallenge, command);
        challengeReviewStore.store(challengeReview);
        return challengeReview.getId();
    }


}
