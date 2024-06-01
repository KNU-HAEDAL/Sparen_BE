package org.haedal.zzansuni.infrastructure.challengegroup.challenge;

import org.haedal.zzansuni.controller.challengegroup.challenge.ChallengeRes.ChallengeCurrentResponse;
import org.haedal.zzansuni.domain.challengegroup.challenge.Challenge;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeCurrentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    @Query(
        "SELECT new org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeCurrentDto("
            +
            "c.id, cg.title, " +
            "(SELECT COUNT(cv) FROM ChallengeVerification cv WHERE cv.userChallenge.challenge.id = c.id AND cv.userChallenge.user.id = :userId), "
            +
            "c.requiredCount, " +
            "uc.createdAt, c.startDate, c.endDate, " +
            "cg.category, " +
            "(SELECT COUNT(cr) > 0 FROM ChallengeReview cr WHERE cr.userChallenge.challenge.id = c.id AND cr.userChallenge.user.id = :userId)"
            +
            ") " +
            "FROM Challenge c " +
            "JOIN c.challengeGroup cg " +
            "JOIN UserChallenge uc ON uc.challenge.id = c.id " +
            "WHERE uc.user.id = :userId " +
            "AND uc.status = org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeStatus.PROCEEDING ")
    Page<ChallengeCurrentDto> findAllCurrentChallenges(@Param("userId") Long userId,
        Pageable pageable);
}

