package org.haedal.zzansuni.challengereview.infrastructure;

import org.haedal.zzansuni.challengegroup.domain.Challenge;
import org.haedal.zzansuni.challengegroup.domain.ChallengeCategory;
import org.haedal.zzansuni.challengegroup.domain.ChallengeGroup;
import org.haedal.zzansuni.challengegroup.infrastructure.ChallengeGroupRepository;
import org.haedal.zzansuni.challengegroup.infrastructure.ChallengeRepository;
import org.haedal.zzansuni.challengereview.domain.ChallengeReview;
import org.haedal.zzansuni.challengereview.domain.ChallengeReviewModel;
import org.haedal.zzansuni.global.security.Role;
import org.haedal.zzansuni.user.domain.User;
import org.haedal.zzansuni.user.infrastructure.UserRepository;
import org.haedal.zzansuni.userchallenge.domain.ChallengeStatus;
import org.haedal.zzansuni.userchallenge.domain.UserChallenge;
import org.haedal.zzansuni.userchallenge.infrastructure.UserChallengeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChallengeReviewReaderImplTest {
    @Autowired private ChallengeGroupRepository challengeGroupRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ChallengeRepository challengeRepository;
    @Autowired private UserChallengeRepository userChallengeRepository;
    @Autowired private ChallengeReviewRepository challengeReviewRepository;
    @Autowired private ChallengeReviewReaderImpl challengeReviewReader;

    @Test
    @Transactional
    @DisplayName("리뷰 평점 count 쿼리가 정상적으로 동작한다.")
    void getScoreModelByChallengeGroupId() {
        // given
        ChallengeGroup challengeGroup = createChallengeGroup();
        Challenge challenge = Challenge.builder()
            .challengeGroup(challengeGroup)
            .requiredCount(2)
            .onceExp(100)
            .successExp(100)
            .difficulty(2)
            .activePeriod(10)
            .build();
        User user = createUser();
        UserChallenge userChallenge = createUserChallenge(challenge, user);

        challengeGroupRepository.save(challengeGroup);
        challengeRepository.save(challenge);
        userRepository.save(user);
        userChallengeRepository.save(userChallenge);

        List<ChallengeReview> reviews = IntStream.range(0, 11)
            .mapToObj(i -> ChallengeReview.builder()
                .challengeGroupId(challenge.getChallengeGroupId())
                .userChallenge(userChallenge)
                .content("content")
                .rating(i % 4 + 1)
                .build())
            .toList();
        // -> 1:3 / 2:3 / 3:3 / 4:2 / 5:0
        challengeReviewRepository.saveAll(reviews);

        // when
        ChallengeReviewModel.Score score = challengeReviewReader.getScoreModelByChallengeGroupId(1L);


        // then
        assertAll(
            () -> assertEquals(3, score.ratingCount().get(1)),
            () -> assertEquals(3, score.ratingCount().get(2)),
            () -> assertEquals(3, score.ratingCount().get(3)),
            () -> assertEquals(2, score.ratingCount().get(4)),
            () -> assertEquals(0, score.ratingCount().get(5))
        );
    }

    private User createUser() {
        return User.builder()
            .nickname("nickname")
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
            .joinStartDate(LocalDate.of(2023, 8, 1))
            .joinEndDate(LocalDate.of(2023, 8, 5))
            .build();
    }

    private UserChallenge createUserChallenge(Challenge challenge, User user) {

        return UserChallenge.builder()
            .challenge(challenge)
            .status(ChallengeStatus.PROCEEDING)
            .activeStartDate(LocalDate.of(2023, 8, 1))
            .activeEndDate(LocalDate.of(2023, 8, 5))
            .user(user)
            .challengeVerifications(new ArrayList<>())
            .build();
    }
}