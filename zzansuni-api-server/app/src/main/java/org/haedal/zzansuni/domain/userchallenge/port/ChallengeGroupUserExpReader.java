package org.haedal.zzansuni.domain.userchallenge.port;

import org.haedal.zzansuni.domain.userchallenge.ChallengeGroupUserExp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ChallengeGroupUserExpReader {
    Optional<ChallengeGroupUserExp> findByChallengeGroupIdAndUserId(Long challengeGroupId, Long userId);

    Page<ChallengeGroupUserExp> getUserExpPagingWithUserByChallengeGroupId(Long challengeGroupId, Pageable pageable);
}
