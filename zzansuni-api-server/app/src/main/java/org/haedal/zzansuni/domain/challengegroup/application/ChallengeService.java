package org.haedal.zzansuni.domain.challengegroup.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.haedal.zzansuni.domain.challengegroup.Challenge;
import org.haedal.zzansuni.domain.challengegroup.ChallengeCommand;
import org.haedal.zzansuni.domain.challengegroup.application.ChallengeModel.ChallengeRecord;
import org.haedal.zzansuni.domain.challengegroup.port.ChallengeReader;
import org.haedal.zzansuni.domain.userchallenge.ChallengeReview;
import org.haedal.zzansuni.domain.userchallenge.application.ChallengeReviewModel;
import org.haedal.zzansuni.domain.userchallenge.application.ChallengeReviewModel.ChallengeReviewWithChallenge;
import org.haedal.zzansuni.domain.userchallenge.application.ChallengeReviewModel.ChallengeReviewWithUserInfo;
import org.haedal.zzansuni.domain.userchallenge.port.ChallengeReviewReader;
import org.haedal.zzansuni.domain.userchallenge.port.ChallengeReviewStore;
import org.haedal.zzansuni.domain.userchallenge.ChallengeVerification;
import org.haedal.zzansuni.domain.userchallenge.application.ChallengeVerificationModel;
import org.haedal.zzansuni.domain.userchallenge.port.ChallengeVerificationReader;
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
    public ChallengeRecord getChallengeRecord(Long userId, Long challengeId) {
        Challenge challenge = challengeReader.getById(challengeId);
        UserChallenge userChallenge
                = userChallengeReader.getByUserIdAndChallengeId(userId, challengeId);
        List<ChallengeVerification> challengeVerifications
                = challengeVerificationReader.findByUserChallengeId(userChallenge.getId());
        return ChallengeRecord.from(challenge, challengeVerifications);

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

        //이미 리뷰를 작성했는지 확인
        challengeReviewReader.findByUserChallengeId(userChallenge.getId())
            .ifPresent(review -> {
                throw new IllegalArgumentException("이미 리뷰를 작성했습니다.");
            });
        ChallengeReview challengeReview = ChallengeReview.create(userChallenge, command);
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
    public ChallengeReviewModel.Score getChallengeGroupReviewScore(
        Long challengeGroupId) {
        List<ChallengeReview> challengeReviews = challengeReviewReader.findByChallengeGroupId(
            challengeGroupId);
        //TODO 모든 리뷰를 가져와서 계산 -> 성능 이슈 발생 가능
        return ChallengeReviewModel.Score.of(challengeReviews);
    }
}
