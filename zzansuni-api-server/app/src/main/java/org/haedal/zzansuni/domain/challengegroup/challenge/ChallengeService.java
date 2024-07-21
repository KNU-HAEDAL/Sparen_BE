package org.haedal.zzansuni.domain.challengegroup.challenge;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroup;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeModel.ChallengeRecord;
import org.haedal.zzansuni.domain.challengegroup.challenge.port.ChallengeReader;
import org.haedal.zzansuni.domain.userchallenge.review.ChallengeReview;
import org.haedal.zzansuni.domain.userchallenge.review.ChallengeReviewModel;
import org.haedal.zzansuni.domain.userchallenge.review.ChallengeReviewModel.ChallengeReviewWithChallenge;
import org.haedal.zzansuni.domain.userchallenge.review.ChallengeReviewModel.ChallengeReviewWithUserInfo;
import org.haedal.zzansuni.domain.userchallenge.review.port.ChallengeReviewReader;
import org.haedal.zzansuni.domain.userchallenge.review.port.ChallengeReviewStore;
import org.haedal.zzansuni.domain.userchallenge.verification.ChallengeVerification;
import org.haedal.zzansuni.domain.userchallenge.verification.ChallengeVerificationModel;
import org.haedal.zzansuni.domain.userchallenge.verification.port.ChallengeVerificationReader;
import org.haedal.zzansuni.domain.userchallenge.UserChallenge;
import org.haedal.zzansuni.domain.userchallenge.port.UserChallengeReader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ChallengeModel.ChallengeRecord getChallengeRecord(Long userId, Long challengeId) {
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
        UserChallenge userChallenge = userChallengeReader.getByUserIdAndChallengeId(userId,
            challengeId);
        Long challengeGroupId = userChallenge
            .getChallenge()
            .getChallengeGroup()
            .getId();

        //이미 리뷰를 작성했는지 확인
        challengeReviewReader.findByUserChallengeId(userChallenge.getId())
            .ifPresent(review -> {
                throw new IllegalArgumentException("이미 리뷰를 작성했습니다.");
            });
        ChallengeReview challengeReview = ChallengeReview.create(userChallenge, command,
            challengeGroupId);
        challengeReviewStore.store(challengeReview);
        return challengeReview.getId();
    }

    /**
     * groupId로 챌린지 리뷰 가져오기
     */
    @Transactional(readOnly = true)
    public Page<ChallengeReviewModel.ChallengeReviewWithChallenge> getChallengeReviewsByGroupId(
        Long challengeGroupId, Pageable pageable) {
        Page<ChallengeReview> challengeReviewPage = challengeReviewReader.getChallengeReviewPageByChallengeGroupId(
            challengeGroupId, pageable);

        return challengeReviewPage.map(ChallengeReviewWithChallenge::from);
    }

    /**
     * 챌린지 리뷰 가져오기
     */
    @Transactional(readOnly = true)
    public Page<ChallengeReviewWithUserInfo> getChallengeReviews(Pageable pageable) {
        Page<ChallengeReview> challengeReviewPage = challengeReviewReader.getChallengeReviewPage(
            pageable);

        return challengeReviewPage.map(ChallengeReviewWithUserInfo::from);
    }

    /**
     * 챌린지 그룹 리뷰 평점 가져오기
     */
    @Transactional(readOnly = true)
    public ChallengeReviewModel.ChallengeReviewScore getChallengeGroupReviewScore(
        Long challengeGroupId) {
        List<ChallengeReview> challengeReviews = challengeReviewReader.findByChallengeGroupId(
            challengeGroupId);
        return ChallengeReviewModel.ChallengeReviewScore.of(challengeReviews);
    }
}
