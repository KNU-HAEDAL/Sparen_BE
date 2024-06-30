package org.haedal.zzansuni.domain.challengegroup;

import org.haedal.zzansuni.domain.challengegroup.challenge.Challenge;
import org.haedal.zzansuni.infrastructure.challengegroup.ChallengeGroupRepository;
import org.haedal.zzansuni.infrastructure.challengegroup.challenge.ChallengeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChallengeGroupServiceTest {
    @Autowired private ChallengeGroupService challengeGroupService;
    @Autowired private ChallengeRepository challengeRepository;
    @Autowired private ChallengeGroupRepository challengeGroupRepository;

    @DisplayName("챌린지 그룹 생성이 정상적으로 동작한다.")
    @Test
    @Transactional
    void createChallengeGroup() {
        LocalDate startDate = LocalDate.of(2021, 1, 1);
        // given
        ChallengeGroupCommand.CreateChallenge createChallenge = ChallengeGroupCommand.CreateChallenge.builder()
                .startDate(startDate)
                .endDate(startDate.plusDays(7))
                .dayType(DayType.WEEK)
                .requiredCount(1)
                .onceExp(100)
                .successExp(100)
                .difficulty(2)
                .build();
        ChallengeGroupCommand.Create command = ChallengeGroupCommand.Create.builder()
            .title("title")
            .guide("guide")
            .content("content")
            .category(ChallengeCategory.VOLUNTEER)
            .createChallenges(List.of(createChallenge))
            .build();

        // when
        challengeGroupService.createChallengeGroup(command);


        // then
        List<ChallengeGroup> groups = challengeGroupRepository.findAll();
        assertThat(groups.size()).isEqualTo(1);

        ChallengeGroup challengeGroup = groups.get(0);
        assertThat(challengeGroup.getTitle()).isEqualTo("title");
        assertThat(challengeGroup.getGuide()).isEqualTo("guide");
        assertThat(challengeGroup.getContent()).isEqualTo("content");
        assertThat(challengeGroup.getCategory()).isEqualTo(ChallengeCategory.VOLUNTEER);

        assertThat(challengeGroup.getChallenges().size()).isEqualTo(1);
        Challenge challenge = challengeGroup.getChallenges().get(0);
        assertThat(challenge.getStartDate()).isEqualTo(startDate);
        assertThat(challenge.getEndDate()).isEqualTo(startDate.plusDays(7));


    }

    @DisplayName("챌린지 그룹 삭제가 정상적으로 동작한다.")
    @Test
    @Transactional
    void deleteChallengeGroup() {
        // given
        ChallengeGroup challengeGroup = ChallengeGroup.builder()
            .title("title")
            .category(ChallengeCategory.VOLUNTEER)
            .content("content")
            .guide("guide")
            .cumulativeCount(0)
            .build();
        challengeGroupRepository.save(challengeGroup);

        Challenge challenge1 = Challenge.builder()
                .challengeGroup(challengeGroup)
                .requiredCount(1)
                .dayType(DayType.WEEK)
                .onceExp(100)
                .successExp(100)
                .difficulty(2)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(7))
                .build();
        Challenge challenge2 = Challenge.builder()
                .challengeGroup(challengeGroup)
                .requiredCount(1)
                .dayType(DayType.WEEK)
                .onceExp(100)
                .successExp(100)
                .difficulty(2)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(7))
                .build();
        challengeRepository.save(challenge1);
        challengeRepository.save(challenge2);


        // when
        challengeGroupService.deleteChallengeGroup(challengeGroup.getId());

        // then
        assertThrows(NoSuchElementException.class, () -> challengeGroupRepository.findById(challengeGroup.getId()).get());
    }
}