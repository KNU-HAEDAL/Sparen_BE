package org.haedal.zzansuni.domain.challengegroup.port;

import org.haedal.zzansuni.domain.challengegroup.ChallengeCategory;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroup;
import org.haedal.zzansuni.domain.challengegroup.application.ChallengeGroupModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChallengeGroupReader {
    ChallengeGroup getById(Long challengeGroupId);

    ChallengeGroup getByIdWithChallenges(Long challengeGroupId);

    Page<ChallengeGroup> getChallengeGroupsPagingByCategory(Pageable pageable, ChallengeCategory category);

    Page<ChallengeGroup> getChallengeGroupsShortsPaging(Pageable pageable, Long userId);

    ChallengeGroupModel.Ranking getRanking(Long challengeGroupId, Long userId);
}
