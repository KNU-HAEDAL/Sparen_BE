package org.haedal.zzansuni.domain.challengegroup.userchallenge;

import java.util.ArrayList;

import org.haedal.zzansuni.domain.challengegroup.*;
import org.haedal.zzansuni.domain.challengegroup.application.ChallengeModel;
import org.haedal.zzansuni.domain.challengegroup.port.ChallengeGroupReader;
import org.haedal.zzansuni.domain.challengegroup.port.ChallengeReader;
import org.haedal.zzansuni.domain.userchallenge.application.AddUserExpByVerificationUseCase;
import org.haedal.zzansuni.domain.userchallenge.ChallengeStatus;
import org.haedal.zzansuni.domain.userchallenge.port.ChallengeReviewReader;
import org.haedal.zzansuni.domain.userchallenge.UserChallenge;
import org.haedal.zzansuni.domain.userchallenge.port.UserChallengeReader;
import org.haedal.zzansuni.domain.userchallenge.application.UserChallengeService;
import org.haedal.zzansuni.domain.userchallenge.port.UserChallengeStore;
import org.haedal.zzansuni.domain.challengegroup.port.ChallengeGroupUserExpStore;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroupUserExp;
import org.haedal.zzansuni.domain.user.User;
import org.haedal.zzansuni.domain.user.UserReader;
import org.haedal.zzansuni.global.security.Role;
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

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserChallengeRecordServiceTest {

    @Mock
    private UserChallengeStore userChallengeStore;

    @Mock
    private UserChallengeReader userChallengeReader;

    @Mock
    private ChallengeReviewReader challengeReviewReader;

    @Mock
    private UserReader userReader;

    @Mock
    private ChallengeReader challengeReader;

    @Mock
    private ChallengeGroupReader challengeGroupReader;

    @Mock
    private ChallengeGroupUserExpStore challengeGroupUserExpStore;

    @Mock
    private AddUserExpByVerificationUseCase addUserExpByVerificationUseCase;

    @InjectMocks
    private UserChallengeService userChallengeService;

    private User user;
    private ChallengeGroup challengeGroup;
    private Challenge challenge;
    private UserChallenge userChallenge;
    private ChallengeGroupUserExp challengeGroupUserExp;

    private Pageable pageable;

    @BeforeEach
    void setUp() {
        user = User.builder()
            .id(1L)
            .email("test@test.com")
            .role(Role.USER)
            .exp(0)
            .nickname("test")
            .password("test")
            .build();

        challengeGroup = ChallengeGroup.builder()
            .id(1L)
            .title("테스트 챌린지 그룹")
            .category(ChallengeCategory.ETC)
            .content("테스트 챌린지 그룹 내용")
            .guide("테스트 챌린지 그룹 가이드")
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
            .challengeVerifications(new ArrayList<>())
            .build();

        challengeGroupUserExp = ChallengeGroupUserExp.builder()
            .id(1L)
            .challengeGroup(challengeGroup)
            .user(user)
            .totalExp(0)
            .build();

        pageable = PageRequest.of(0, 10);
    }

    @Test
    void participateChallenge_신규_챌린지() {
        Long userId = 1L;
        Long challengeId = 1L;

        when(userReader.getById(userId)).thenReturn(user);
        when(challengeReader.getById(challengeId)).thenReturn(challenge);
        when(userChallengeReader.findByUserIdAndChallengeId(userId, challengeId)).thenReturn(
            Optional.empty());

        userChallengeService.participateChallenge(userId, challengeId);

        verify(userReader).getById(userId);
        verify(challengeReader).getById(challengeId);
        verify(userChallengeReader).findByUserIdAndChallengeId(userId, challengeId);
        verify(userChallengeStore).store(any(UserChallenge.class));
    }

    @Test
    void participateChallenge_이미_참여중인_챌린지() {
        Long userId = 1L;
        Long challengeId = 1L;

        when(userReader.getById(userId)).thenReturn(user);
        when(challengeReader.getById(challengeId)).thenReturn(challenge);
        when(userChallengeReader.findByUserIdAndChallengeId(userId, challengeId)).thenReturn(
            Optional.of(userChallenge));

        assertThrows(IllegalArgumentException.class,
            () -> userChallengeService.participateChallenge(userId, challengeId));

        verify(userReader).getById(userId);
        verify(challengeReader).getById(challengeId);
        verify(userChallengeReader).findByUserIdAndChallengeId(userId, challengeId);
        verify(userChallengeStore, never()).store(any(UserChallenge.class));
    }

    @Test
    void verification() {
        Long userId = 1L;
        Long challengeId = 1L;
        ChallengeCommand.VerificationCreate command = new ChallengeCommand.VerificationCreate(
            "인증 내용", "http://example.com/image.jpg");

        when(userChallengeReader.getByChallengeIdWithVerificationAndChallenge(challengeId,
            userId)).thenReturn(userChallenge);
        ChallengeModel.ChallengeVerificationResult result = userChallengeService.verification(
            challengeId, userId, command);

        assertNotNull(result);
        assertEquals(challenge.getRequiredCount(), result.totalCount());
        assertEquals(1, result.successCount());
        assertEquals(challenge.getOnceExp(), result.obtainExp());

        verify(userChallengeReader).getByChallengeIdWithVerificationAndChallenge(challengeId,
            userId);
    }

    @Test
    void getCurrentChallenges() {
        Page<UserChallenge> userChallengePage = new PageImpl<>(
            Collections.singletonList(userChallenge));

        when(
            userChallengeReader.getCurrentChallengePageByUserId(user.getId(), pageable)).thenReturn(
            userChallengePage);
        when(challengeReviewReader.getReviewWrittenMapByUserChallengeId(
            Collections.singletonList(userChallenge.getId())))
            .thenReturn(Collections.singletonMap(userChallenge.getId(), true));

        Page<ChallengeModel.ChallengeCurrent> resultPage = userChallengeService.getCurrentChallenges(
            user.getId(), pageable);

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getContent().size());
        assertEquals(challenge.getId(), resultPage.getContent().get(0).challengeId());
        assertTrue(resultPage.getContent().get(0).reviewWritten());

        verify(userChallengeReader).getCurrentChallengePageByUserId(user.getId(), pageable);
        verify(challengeReviewReader).getReviewWrittenMapByUserChallengeId(
            Collections.singletonList(userChallenge.getId()));
    }

    @Test
    void getCompleteChallenges() {
        Page<UserChallenge> userChallengePage = new PageImpl<>(
            Collections.singletonList(userChallenge));

        when(userChallengeReader.getCompletedChallengePageByUserId(user.getId(),
            pageable)).thenReturn(userChallengePage);
        when(challengeReviewReader.getReviewWrittenMapByUserChallengeId(
            Collections.singletonList(userChallenge.getId())))
            .thenReturn(Collections.singletonMap(userChallenge.getId(), true));

        Page<ChallengeModel.ChallengeComplete> resultPage = userChallengeService.getCompleteChallenges(
            user.getId(), pageable);

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getContent().size());
        assertEquals(challenge.getId(), resultPage.getContent().get(0).challengeId());
        assertTrue(resultPage.getContent().get(0).reviewWritten());

        verify(userChallengeReader).getCompletedChallengePageByUserId(user.getId(), pageable);
        verify(challengeReviewReader).getReviewWrittenMapByUserChallengeId(
            Collections.singletonList(userChallenge.getId()));
    }
}
