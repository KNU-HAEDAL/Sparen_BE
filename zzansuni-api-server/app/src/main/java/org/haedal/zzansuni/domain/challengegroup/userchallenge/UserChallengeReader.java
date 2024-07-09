package org.haedal.zzansuni.domain.challengegroup.userchallenge;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserChallengeReader {

    UserChallenge getById(Long id);

    UserChallenge getByIdWithVerificationAndChallenge(Long id);

    UserChallenge getByChallengeIdWithVerificationAndChallenge(Long challengeId, Long userId);

    Optional<UserChallenge> findById(Long id);

    UserChallenge getByUserIdAndChallengeId(Long userId, Long challengeId);

    Optional<UserChallenge> findByUserIdAndChallengeId(Long userId, Long challengeId);

    Page<UserChallenge> getCurrentChallengePageByUserId(Long userId, Pageable pageable);

    Page<UserChallenge> getCompletedChallengePageByUserId(Long userId, Pageable pageable);

    List<Pair<LocalDate,Integer>> findAllByUserIdAndCreatedAt(Long userId, LocalDate startDate, LocalDate endDate);
}
