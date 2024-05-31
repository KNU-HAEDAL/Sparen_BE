package org.haedal.zzansuni.domain.challengegroup.challenge;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.haedal.zzansuni.controller.PagingRequest;
import org.haedal.zzansuni.controller.PagingResponse;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroup;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeCommand.ReviewCreate;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeCommand.Verificate;
import org.haedal.zzansuni.domain.challengegroup.challengereview.ChallengeReview;
import org.haedal.zzansuni.domain.challengegroup.challengereview.ChallengeReviewReader;
import org.haedal.zzansuni.domain.challengegroup.challengereview.ChallengeReviewStore;
import org.haedal.zzansuni.domain.challengegroup.challengeverification.ChallengeVerification;
import org.haedal.zzansuni.domain.challengegroup.challengeverification.ChallengeVerificationModel;
import org.haedal.zzansuni.domain.challengegroup.challengeverification.ChallengeVerificationReader;
import org.haedal.zzansuni.domain.challengegroup.challengeverification.ChallengeVerificationStore;
import org.haedal.zzansuni.domain.challengegroup.userchallenge.UserChallenge;
import org.haedal.zzansuni.domain.challengegroup.userchallenge.UserChallengeReader;
import org.springframework.data.domain.Page;
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

    private final ChallengeReviewStore challengeReviewStore;
    private final ChallengeReviewReader challengeReviewReader;

    /**
     * 챌린지 인증하기 1. ChallengeVerification 테이블에 데이터 추가 2. Challenge 엔티티에서 필요참여횟수 가져오기 3. UserChallenge
     * 엔티티에서 참여횟수 가져오기 4. 참여횟수와 필요참여횟수가 같으면 챌린지 완료로 변경
     */
    public ChallengeModel.ChallengeVerificationResult verification(Long userChallengeId,
        Verificate verificate) {
        UserChallenge userChallenge = userChallengeReader.getById(userChallengeId);
        // TODO 이미지 업로드 로직 필요
        ChallengeVerification challengeVerification = ChallengeVerification.create(verificate,
            userChallenge);
        challengeVerificationStore.store(challengeVerification);

        /**
         * 챌린지 RequiredCount 가져오기 위해 챌린지 정보 가져온다
         */
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

    /**
     * 챌린지 기록 가져오기
     */
    public ChallengeModel.ChallengeRecordDto getChallengeRecord(Long userId, Long challengeId) {
        Challenge challenge = challengeReader.getById(challengeId);
        ChallengeGroup challengeGroup = challenge.getChallengeGroup();
        UserChallenge userChallenge = userChallengeReader.getByUserIdAndChallengeId(userId,
            challengeId);
        return ChallengeModel.ChallengeRecordDto
            .from(challenge, challengeGroup,
                challengeVerificationReader.findByUserChallengeId(userChallenge.getId()));

    }

    /**
     * 챌린지 기록 상세 가져오기
     */
    public ChallengeVerificationModel getChallengeRecordDetail(Long recordId) {
        return ChallengeVerificationModel.from(challengeVerificationReader.getById(recordId));
    }

    /**
     * 챌린지 리뷰 작성하기
     */
    public Long reviewCreate(ReviewCreate command, Long challengeId, Long id) {
        UserChallenge userChallenge = userChallengeReader.getByUserIdAndChallengeId(id,
            challengeId);
        ChallengeReview challengeReview = ChallengeReview.create(userChallenge,
            command.getContent(),
            command.getRating());
        /**
         * 이미 리뷰를 작성했는지 확인
         */
        challengeReviewReader.findByUserChallengeId(challengeId)
            .ifPresent(review -> {
                throw new IllegalArgumentException("이미 리뷰를 작성했습니다.");
            });

        challengeReviewStore.store(challengeReview);
        return challengeReview.getId();
    }
}
