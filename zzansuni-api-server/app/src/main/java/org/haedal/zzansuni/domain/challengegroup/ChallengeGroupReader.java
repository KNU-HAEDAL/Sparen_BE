package org.haedal.zzansuni.domain.challengegroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ChallengeGroupReader {
    ChallengeGroup getById(Long challengeGroupId);

    ChallengeGroup getByIdWithChallenges(Long challengeGroupId);

    Page<ChallengeGroup> getChallengeGroupsPagingByCategory(Pageable pageable, ChallengeCategory category);

    Page<ChallengeGroup> getChallengeGroupsShortsPaging(Pageable pageable, Long userId);

    Optional<ChallengeGroupUserExp> findByChallengeGroupIdAndUserId(Long challengeGroupId, Long userId);
}
