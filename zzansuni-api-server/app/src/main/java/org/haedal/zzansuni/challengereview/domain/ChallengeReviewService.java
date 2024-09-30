package org.haedal.zzansuni.challengereview.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.haedal.zzansuni.userchallenge.domain.port.UserChallengeReader;
import org.haedal.zzansuni.userchallenge.domain.UserChallenge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChallengeReviewService {
    private final ChallengeReviewStore challengeReviewStore;
    private final ChallengeReviewReader challengeReviewReader;
    private final UserChallengeReader userChallengeReader;


    /**
     * 챌린지 리뷰 작성하기 / 수정하기
     */
    @Transactional
    public Long upsertReview(ChallengeReviewCommand.Upsert command, Long challengeId, Long userId) {
        UserChallenge userChallenge = userChallengeReader.findByUserIdAndChallengeId(userId,
            challengeId).orElseThrow(() -> new IllegalStateException("해당 챌린지 참여 기록이 없습니다."));

        ChallengeReview challengeReview = challengeReviewReader.findByUserChallengeId(userChallenge.getId())
            .map(review -> {
                review.update(command);
                return review;
            })
            .orElseGet(() -> ChallengeReview.create(userChallenge, command));
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

        return challengeReviewPage.map(ChallengeReviewModel.ChallengeReviewWithChallenge::from);
    }

    /**
     * 챌린지 리뷰 가져오기
     */
    @Transactional(readOnly = true)
    public Page<ChallengeReviewModel.ChallengeReviewWithUserInfo> getChallengeReviews(Pageable pageable) {
        Page<ChallengeReview> challengeReviewPage = challengeReviewReader.getChallengeReviewPage(
            pageable);

        return challengeReviewPage.map(ChallengeReviewModel.ChallengeReviewWithUserInfo::from);
    }

    /**
     * 챌린지 그룹 리뷰 평점 가져오기
     */
    @Transactional(readOnly = true)
    public ChallengeReviewModel.Score getChallengeGroupReviewScore(
        Long challengeGroupId) {
        return challengeReviewReader.getScoreModelByChallengeGroupId(challengeGroupId);
    }
}
