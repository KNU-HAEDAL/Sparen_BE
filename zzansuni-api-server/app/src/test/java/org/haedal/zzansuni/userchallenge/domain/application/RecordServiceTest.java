package org.haedal.zzansuni.userchallenge.domain.application;

import java.time.LocalDate;

import org.haedal.zzansuni.challengegroup.domain.*;
import org.haedal.zzansuni.challengegroup.domain.port.ChallengeReader;
import org.haedal.zzansuni.challengereview.domain.ChallengeReview;
import org.haedal.zzansuni.challengereview.domain.ChallengeReviewModel;
import org.haedal.zzansuni.challengereview.domain.ChallengeReviewService;
import org.haedal.zzansuni.userchallenge.domain.*;
import org.haedal.zzansuni.challengereview.domain.ChallengeReviewReader;
import org.haedal.zzansuni.challengereview.domain.ChallengeReviewStore;
import org.haedal.zzansuni.userchallenge.domain.port.ChallengeVerificationReader;
import org.haedal.zzansuni.userchallenge.domain.port.UserChallengeReader;
import org.haedal.zzansuni.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecordServiceTest {

    @Mock
    private ChallengeReader challengeReader;

    @Mock
    private UserChallengeReader userChallengeReader;

    @Mock
    private ChallengeVerificationReader challengeVerificationReader;

    @Mock
    private ChallengeReviewStore challengeReviewStore;

    @Mock
    private ChallengeReviewReader challengeReviewReader;

    @InjectMocks
    private ChallengeRecordService challengeRecordService;

    @InjectMocks
    private ChallengeReviewService challengeReviewService;

    private Challenge challenge;
    private ChallengeGroup challengeGroup;
    private UserChallenge userChallenge;
    private ChallengeVerification challengeVerification;
    private ChallengeReview challengeReview;
    private User user;
    private Pageable pageable;


    @BeforeEach
    void setUp() {
        user = User.builder()
            .id(1L)
            .email("test@test.com")
            .nickname("test")
            .password("test")
            .build();

        challengeGroup = ChallengeGroup.builder()
            .id(1L)
            .title("Test Challenge Group")
            .category(ChallengeCategory.ETC)
            .content("Test Challenge Group Content")
            .guide("Test Challenge Group Guide")
            .cumulativeCount(0)
            .build();

        challenge = Challenge.builder()
            .id(1L)
            .challengeGroup(challengeGroup)
            .requiredCount(10)
            .dayType(DayType.DAY)
            .onceExp(10)
            .successExp(50)
            .difficulty(1)
            .startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(30))
            .build();

        userChallenge = UserChallenge.builder()
            .id(1L)
            .challenge(challenge)
            .status(ChallengeStatus.PROCEEDING)
            .user(user)
            .build();

        challengeVerification = ChallengeVerification.builder()
            .id(1L)
            .userChallenge(userChallenge)
            .imageUrl("http://example.com/image.jpg")
            .content("Test Verification")
            .status(ChallengeVerificationStatus.PRE_APPROVED)
            .build();

        challengeReview = ChallengeReview.builder()
            .id(1L)
            .userChallenge(userChallenge)
            .content("Great challenge!")
            .rating(5)
            .challengeGroupId(challengeGroup.getId())
            .build();

        pageable = PageRequest.of(0, 10);
    }

    @Test
    void getChallengeRecord() {
        Long userId = 1L;
        Long challengeId = 1L;
        when(challengeReader.getById(challengeId)).thenReturn(challenge);
        when(userChallengeReader.getByUserIdAndChallengeId(userId, challengeId)).thenReturn(
            userChallenge);
        when(challengeVerificationReader.findByUserChallengeId(userChallenge.getId())).thenReturn(
            Collections.singletonList(challengeVerification));

        UserChallengeModel.Record result = challengeRecordService.getChallengeRecord(userId, challengeId);

        assertNotNull(result);
        verify(challengeReader).getById(challengeId);
        verify(userChallengeReader).getByUserIdAndChallengeId(userId, challengeId);
        verify(challengeVerificationReader).findByUserChallengeId(userChallenge.getId());
    }

    @Test
    void getChallengeRecordDetail() {
        Long recordId = 1L;
        when(challengeVerificationReader.getById(recordId)).thenReturn(challengeVerification);

        ChallengeVerificationModel result = challengeRecordService.getChallengeRecordDetail(recordId);

        assertNotNull(result);
        verify(challengeVerificationReader).getById(recordId);
    }

    @Test
    void createReview() {
        Long userId = 1L;
        Long challengeId = 1L;
        ChallengeCommand.ReviewCreate command = new ChallengeCommand.ReviewCreate(
            "Great challenge!", 5);

        when(userChallengeReader.findByUserIdAndChallengeId(userId, challengeId)).thenReturn(
            Optional.of(userChallenge));
        // Mock ChallengeReviewReader to return an empty Optional
        when(challengeReviewReader.findByUserChallengeId(userChallenge.getId())).thenReturn(
            Optional.empty());

        // Mock ChallengeReviewStore to capture the stored ChallengeReview
        when(challengeReviewStore.store(any())).thenAnswer(invocation -> {
            ChallengeReview review = invocation.getArgument(0);
            return ChallengeReview.builder()
                .id(1L)  // ID를 설정하여 새로운 리뷰 객체 생성
                .userChallenge(review.getUserChallenge())
                .content(review.getContent())
                .rating(review.getRating())
                .challengeGroupId(review.getChallengeGroupId())
                .build();
        });

        //TODO: ChallengeReview가 정적메서드라 테스트하기 어려움
        Long reviewId = challengeReviewService.createReview(command, challengeId, userId);

        //assertNotNull(reviewId);  // 리뷰 ID가 null이 아닌지 확인
        //assertEquals(1L, reviewId);  // 리뷰 ID가 1L인지 확인
        verify(userChallengeReader).findByUserIdAndChallengeId(userId, challengeId);
        verify(challengeReviewReader).findByUserChallengeId(userChallenge.getId());
        verify(challengeReviewStore).store(any());
    }


    @Test
    void getChallengeReviewsByGroupId() {
        Long challengeGroupId = 1L;
        Page<ChallengeReview> challengeReviewPage = new PageImpl<>(
            Collections.singletonList(challengeReview));
        when(challengeReviewReader.getChallengeReviewPageByChallengeGroupId(challengeGroupId,
            pageable)).thenReturn(challengeReviewPage);

        Page<ChallengeReviewModel.ChallengeReviewWithChallenge> result = challengeReviewService.getChallengeReviewsByGroupId(
            challengeGroupId, pageable);

        assertNotNull(result);
        verify(challengeReviewReader).getChallengeReviewPageByChallengeGroupId(challengeGroupId,
            pageable);
    }

    @Test
    void getChallengeReviews() {
        Page<ChallengeReview> challengeReviewPage = new PageImpl<>(
            Collections.singletonList(challengeReview));
        when(challengeReviewReader.getChallengeReviewPage(pageable)).thenReturn(
            challengeReviewPage);

        Page<ChallengeReviewModel.ChallengeReviewWithUserInfo> result = challengeReviewService.getChallengeReviews(
            pageable);

        assertNotNull(result);
        verify(challengeReviewReader).getChallengeReviewPage(pageable);
    }

    @Test
    void getChallengeGroupReviewScore() {
        Long challengeGroupId = 1L;
        List<ChallengeReview> challengeReviews = Collections.singletonList(challengeReview);
        when(challengeReviewReader.findByChallengeGroupId(challengeGroupId)).thenReturn(
            challengeReviews);

        ChallengeReviewModel.Score result = challengeReviewService.getChallengeGroupReviewScore(
            challengeGroupId);

        assertNotNull(result);
        verify(challengeReviewReader).findByChallengeGroupId(challengeGroupId);
    }
}
