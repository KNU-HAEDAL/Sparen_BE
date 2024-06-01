package org.haedal.zzansuni.infrastructure.challengegroup.challenge;

import org.haedal.zzansuni.domain.challengegroup.challenge.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

//    /**
//     * userId로 현재 진행중인 챌린지 조회 <br> userId로 챌린지를 가져온다.
//     */
//    @Query(
//        "SELECT new org.haedal.zzansuni.infrastructure.challengegroup.challenge.dto.ChallengeCurrentDto("
//            + "c.id, c.title, ,"
//            + " c.startDate, c.endDate,"
//            + " c.participantCount, c.participantLimit, c.status, c.user.id, c.user.nickname, c.user.profileImage) "
//            + "FROM Challenge c WHERE c.user.id = :userId")
//    Page<ChallengeCurrentDto> findAllChallengeCurrent(Long userId, Pageable pageable);

}

