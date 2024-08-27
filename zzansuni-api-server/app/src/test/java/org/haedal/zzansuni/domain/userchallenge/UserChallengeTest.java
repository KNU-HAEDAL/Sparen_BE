package org.haedal.zzansuni.domain.userchallenge;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.util.ArrayList;
import org.haedal.zzansuni.domain.challengegroup.Challenge;
import org.haedal.zzansuni.domain.challengegroup.ChallengeCategory;
import org.haedal.zzansuni.domain.challengegroup.ChallengeCommand;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroup;
import org.haedal.zzansuni.domain.challengegroup.DayType;
import org.haedal.zzansuni.domain.user.User;
import org.haedal.zzansuni.global.security.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserChallengeTest {

    @Test
    @DisplayName("챌린지_인증_추가")
    void 유저_챌린지_인증_추가() {
        // given
        UserChallenge userChallenge = createUserChallenge();
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
    @DisplayName("챌린지_인증중_성공한_가장_최근_날짜를_가져온다.")
    void 유저_챌린지_성공일자_가져오기() {
        // given
        // when
        // then
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
            .endDate(LocalDate.of(2021, 1, 1).plusDays(7))
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
}
