package org.haedal.zzansuni.infrastructure.challengegroup.userchallenge;

import java.util.Optional;
import org.haedal.zzansuni.domain.challengegroup.userchallenge.UserChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserChallengeRepository extends JpaRepository<UserChallenge, Long> {

    Optional<UserChallenge> findByUserIdAndChallengeId(Long userId, Long challengeId);

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
}
