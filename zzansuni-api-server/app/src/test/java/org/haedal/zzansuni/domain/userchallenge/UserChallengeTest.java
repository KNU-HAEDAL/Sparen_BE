package org.haedal.zzansuni.domain.userchallenge;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.haedal.zzansuni.domain.challengegroup.Challenge;
import org.haedal.zzansuni.domain.challengegroup.ChallengeCategory;
import org.haedal.zzansuni.domain.challengegroup.ChallengeCommand;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroup;
import org.haedal.zzansuni.domain.challengegroup.DayType;
import org.haedal.zzansuni.domain.user.User;
import org.haedal.zzansuni.global.security.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class UserChallengeTest {

    @Test
    @DisplayName("챌린지_인증_추가")
    void 유저_챌린지_인증_추가() {
        // given
        Challenge challenge = Challenge.builder()
            .challengeGroup(createChallengeGroup())
            .requiredCount(2)
            .dayType(DayType.WEEK)
            .onceExp(100)
            .successExp(100)
            .difficulty(2)
            .startDate(LocalDate.of(2021, 1, 1))
            .endDate(LocalDate.of(2024, 1, 1))
            .build();

        UserChallenge userChallenge = createUserChallenge(challenge);
        ChallengeCommand.VerificationCreate command = ChallengeCommand.VerificationCreate.builder()
            .content("content")
            .imageUrl("imageUrl")
            .build();
        // when
        AddUserExpByVerificationEvent event = userChallenge.addChallengeVerification(command);

        // then
        assertAll(
            () -> assertThat(userChallenge.getChallengeVerifications().size()).isEqualTo(1),
            () -> assertThat(event.getAcquiredExp()).isEqualTo(100)
        );
    }

    @Test
    @DisplayName("인증_조건_횟수를_채운_경우_챌린지_상태가_완료로_변경된다.")
    void 유저_챌린지_완료_상태_변경() {
        // given
        UserChallenge userChallenge = createUserChallenge();
        ChallengeCommand.VerificationCreate command = ChallengeCommand.VerificationCreate.builder()
            .content("content")
            .imageUrl("imageUrl")
            .build();
        // when
        for (int i = 0; i < userChallenge.getChallenge().getRequiredCount(); i++) {
            userChallenge.addChallengeVerification(command);
        }
        // then
        assertThat(userChallenge.getStatus()).isEqualTo(ChallengeStatus.SUCCESS);
    }

    @Test
    @DisplayName("챌린지_완료_상태인_경우_인증_추가_불가능")
    void 유저_챌린지_완료_상태_인증_추가_불가능() {
        // TODO: 챌린지_완료_상태인_경우_인증_추가_불가능 기능 필요
    }

    @Test
    @DisplayName("챌린지_인증중_성공한_가장_최근_날짜를_가져온다.")
    void 유저_챌린지_성공일자_가져오기() {
        // given
        UserChallenge userChallenge = createUserChallenge();
        ChallengeCommand.VerificationCreate command1 = ChallengeCommand.VerificationCreate.builder()
            .content("content")
            .imageUrl("imageUrl")
            .build();

        ChallengeCommand.VerificationCreate command2 = ChallengeCommand.VerificationCreate.builder()
            .content("content")
            .imageUrl("imageUrl")
            .build();

        ChallengeVerification verification1 = ChallengeVerification.create(command1, userChallenge);
        ChallengeVerification verification2 = ChallengeVerification.create(command2, userChallenge);

        // 생성일자 강제 설정
        ReflectionTestUtils.setField(verification1, "createdAt",
            LocalDateTime.of(2023, 8, 4, 0, 0));
        ReflectionTestUtils.setField(verification2, "createdAt",
            LocalDateTime.of(2023, 8, 2, 0, 0));

        // 챌린지 인증 추가
        userChallenge.getChallengeVerifications().addAll(
            List.of(verification1, verification2));

        // when
        Optional<LocalDate> successDate = userChallenge.getSuccessDate();

        // then
        assertThat(successDate.get()).isEqualTo(LocalDate.of(2023, 8, 4));
    }

    private User createUser(Long id, String nickname) {
        return User.builder()
            .id(id)
            .nickname(nickname)
            .email(null)
            .password(null)
            .role(Role.USER)
            .provider(null)
            .authToken(null)
            .exp(0)
            .profileImageUrl(null)
            .build();
    }

    private ChallengeGroup createChallengeGroup() {
        return ChallengeGroup.builder()
            .title("title")
            .category(ChallengeCategory.VOLUNTEER)
            .content("content")
            .guide("guide")
            .cumulativeCount(0)
            .build();
    }

    private Challenge createChallenge() {
        ChallengeGroup challengeGroup = createChallengeGroup();

        return Challenge.builder()
            .challengeGroup(challengeGroup)
            .requiredCount(2)
            .dayType(DayType.WEEK)
            .onceExp(100)
            .successExp(100)
            .difficulty(2)
            .startDate(LocalDate.of(2021, 1, 1))
            .endDate(LocalDate.of(2024, 1, 1))
            .build();
    }

    private UserChallenge createUserChallenge() {
        User user = createUser(1L, "test");
        Challenge challenge = createChallenge();

        return UserChallenge.builder()
            .id(1L)
            .challenge(challenge)
            .status(ChallengeStatus.PROCEEDING)
            .user(user)
            .challengeVerifications(new ArrayList<>())
            .build();
    }

    private UserChallenge createUserChallenge(Challenge challenge) {
        User user = createUser(1L, "test");

        return UserChallenge.builder()
            .id(1L)
            .challenge(challenge)
            .status(ChallengeStatus.PROCEEDING)
            .user(user)
            .challengeVerifications(new ArrayList<>())
            .build();
    }
}
