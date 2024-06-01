package org.haedal.zzansuni.domain.challengegroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChallengeGroupReader {
    ChallengeGroup getById(Long challengeGroupId);

    ChallengeGroup getByIdWithChallenges(Long challengeGroupId);

    Page<ChallengeGroup> getChallengeGroupsPagingByCategory(Pageable pageable, ChallengeCategory category);
}
