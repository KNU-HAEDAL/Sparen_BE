package org.haedal.zzansuni.challengegroup.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChallengeGroupTest {

    @Test
    @DisplayName("챌린지 그룹 업데이트가 정상적으로 동작한다.")
    void update() {
        // given
        // 챌린지 그룹1, 챌린지1,2,3 생성
        List<Challenge> challenges = new ArrayList<>();
        ChallengeGroup challengeGroup = ChallengeGroup.builder()
            .id(1L)
            .challenges(challenges)
            .build();

        Challenge challenge1 = Challenge.builder()
            .id(1L)
            .challengeGroup(challengeGroup)
            .build();

        Challenge challenge2 = Challenge.builder()
            .id(2L)
            .challengeGroup(challengeGroup)
            .build();
        Challenge challenge3 = Challenge.builder()
            .id(3L)
            .challengeGroup(challengeGroup)
            .build();

        challenges.add(challenge1);
        challenges.add(challenge2);
        challenges.add(challenge3);


        // 챌린지 update 1,2,3 생성
        ChallengeGroupCommand.UpdateChallenge updateChallenge1 = ChallengeGroupCommand.UpdateChallenge.builder()
            .id(1L)
            .requiredCount(1)
            .onceExp(1)
            .successExp(1)
            .difficulty(1)
            .activePeriod(1)
            .build();

        ChallengeGroupCommand.UpdateChallenge updateChallenge2 = ChallengeGroupCommand.UpdateChallenge.builder()
            .id(2L)
            .requiredCount(2)
            .onceExp(2)
            .successExp(2)
            .difficulty(2)
            .activePeriod(2)
            .build();

        ChallengeGroupCommand.UpdateChallenge updateChallenge3 = ChallengeGroupCommand.UpdateChallenge.builder()
            .id(3L)
            .requiredCount(3)
            .onceExp(3)
            .successExp(3)
            .difficulty(3)
            .activePeriod(3)
            .build();

        ChallengeGroupCommand.Update command = ChallengeGroupCommand.Update.builder()
            .id(1L)
            .title("title")
            .content("content")
            .guide("guide")
            .joinStartDate(LocalDate.now())
            .joinEndDate(LocalDate.now().plusDays(1))
            .category(ChallengeCategory.ETC)
            .createChallenges(List.of())
            .updateChallenges(List.of(updateChallenge1, updateChallenge2, updateChallenge3))
            .build();

        // when
        challengeGroup.update(command);

        // then
        assertEquals(3, challengeGroup.getChallenges().size());
    }
}