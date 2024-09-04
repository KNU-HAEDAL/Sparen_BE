package org.haedal.zzansuni.challengegroup.domain.application;

import jakarta.persistence.EntityManager;
import org.haedal.zzansuni.challengegroup.domain.*;
import org.haedal.zzansuni.challengegroup.infrastructure.ChallengeGroupImageRepository;
import org.haedal.zzansuni.challengegroup.infrastructure.ChallengeGroupRepository;
import org.haedal.zzansuni.challengegroup.infrastructure.ChallengeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class ChallengeGroupQueryServiceTest {

    @Autowired
    ChallengeGroupQueryService challengeGroupQueryService;
    @Autowired
    ChallengeGroupRepository challengeGroupRepository;
    @Autowired
    ChallengeRepository challengeRepository;
    @Autowired
    ChallengeGroupImageRepository challengeGroupImageRepository;
    @Autowired
    EntityManager em;

    @DisplayName("챌린지 그룹 목록 페이징이 정상적으로 동작한다.")
    @Test
    void getChallengeGroupsPaging() {
        // given
        for (int i = 0; i < 20; i++) {
            ChallengeGroup challengeGroup = createChallengeGroup("title" + i,
                ChallengeCategory.ECHO);
            challengeGroupRepository.save(challengeGroup);
        }
        for (int i = 0; i < 10; i++) {
            ChallengeGroup challengeGroup = createChallengeGroup("title" + i,
                ChallengeCategory.VOLUNTEER);
            challengeGroupRepository.save(challengeGroup);
        }

        // when
        Pageable pageable = PageRequest.of(0, 10);
        ChallengeCategory category = ChallengeCategory.ECHO;

        em.clear();

        // then
        Page<ChallengeGroupModel.Info> challengeGroupsPaging = challengeGroupQueryService.getChallengeGroupsPaging(
            pageable, category);

        assertEquals(10, challengeGroupsPaging.getContent().size());
        assertEquals(2, challengeGroupsPaging.getTotalPages());

    }

    @DisplayName("챌린지 그룹 상세 조회가 정상적으로 동작한다.")
    @Test
    void getChallengeGroupDetail() {
        // given
        ChallengeGroup challengeGroup = createChallengeGroup("title", ChallengeCategory.ECHO);
        challengeGroupRepository.save(challengeGroup);

        Challenge challenge = Challenge.builder()
            .challengeGroup(challengeGroup)
            .requiredCount(12)
            .successExp(100)
            .onceExp(10)
            .difficulty(3)
            .activePeriod(7)
            .build();
        challengeRepository.save(challenge);

        ChallengeGroupImage challengeGroupImage = ChallengeGroupImage.builder()
            .challengeGroup(challengeGroup)
            .imageUrl("image-url")
            .build();
        challengeGroupImageRepository.save(challengeGroupImage);

        em.clear();
        // when
        ChallengeGroupModel.Detail model = challengeGroupQueryService.getChallengeGroupDetail(
            challengeGroup.getId());

        // then
        assertEquals(challengeGroup.getId(), model.id());
        assertEquals(model.challenges().size(), 1);
        assertEquals(challenge.getId(), model.challenges().get(0).id());
        assertEquals(model.imageUrls().size(), 1);
        assertEquals(challengeGroupImage.getImageUrl(), model.imageUrls().get(0));

    }

    @Test
    void getChallengeGroupsShortsPaging() {
    }

    @Test
    void getChallengeGroupRankingsPaging() {
    }

    @Test
    void getChallengeGroupRanking() {
    }


    private ChallengeGroup createChallengeGroup(String title, ChallengeCategory category) {
        return ChallengeGroup.builder()
            .title(title)
            .category(category)
            .content("content")
            .guide("guide")
            .cumulativeCount(0)
            .joinStartDate(LocalDate.now())
            .joinEndDate(LocalDate.now().plusDays(10))
            .build();
    }
}