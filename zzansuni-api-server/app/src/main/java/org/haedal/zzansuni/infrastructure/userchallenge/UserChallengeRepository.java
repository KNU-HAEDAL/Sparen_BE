package org.haedal.zzansuni.infrastructure.userchallenge;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

import org.haedal.zzansuni.domain.userchallenge.DayCountType;
import org.haedal.zzansuni.domain.userchallenge.UserChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserChallengeRepository extends JpaRepository<UserChallenge, Long> {
    Optional<UserChallenge> findByUserIdAndChallenge_Id(Long userId, Long challengeId);

    /**
     * [challengeVerifications]와 [challenge]를 [fetchJoin]으로 OneToMany를 가져온다.
     */
    @Query("SELECT uc FROM UserChallenge uc " +
            "LEFT JOIN FETCH uc.challengeVerifications " +
            "LEFT JOIN FETCH uc.challenge " +
            "WHERE uc.id = :id")
    Optional<UserChallenge> findByIdWithFetchLazy(@Param("id") Long id);

    @Query("SELECT uc FROM UserChallenge uc " +
            "LEFT JOIN FETCH uc.challengeVerifications " +
            "LEFT JOIN FETCH uc.challenge " +
            "WHERE uc.challenge.id = :challengeId " +
            "AND uc.user.id = :userId")
    Optional<UserChallenge> findByChallengeIdWithFetchLazy(
            @Param("challengeId") Long challengeId,
            @Param("userId") Long userId
    );
    /**
     * mysql
     * 유저의 스트릭을 조회하는 쿼리
     * userId로 챌린지 찾고, userChallenge의 생성시점과 startDate를 비교하고
     * challengeVerification을 left join하여 가져옴
     * verification의 생성시점과 endDate를 비교하고 필터링된 레코드를 날짜기준 그룹화 -> 정렬
     *
     * 이 쿼리가 userChallenge에 있는게 맞는지, challengeVerification에 있는게 맞는지
     */
    @Query("SELECT DATE(cv.createdAt) as date, count(*) as count FROM UserChallenge uc " +
            "JOIN uc.challengeVerifications cv " +
            "WHERE uc.user.id = :userId " +
            "AND uc.id = cv.userChallenge.id " +
            "AND DATE(uc.createdAt) >= :startDate " +
            "AND DATE(cv.createdAt) <= :endDate " +
            "AND cv.status = 'APPROVED' " +
            "GROUP BY DATE(cv.createdAt) " +
            "ORDER BY DATE(cv.createdAt)")
    List<DayCountType> countAllByUserIdAndDate(
            @Param("userId") Long userId,
            @Param("startDate")LocalDate startDate,
            @Param("endDate")LocalDate endDate
    );
}
