package org.haedal.zzansuni.challengegroup.domain.port;

import org.haedal.zzansuni.challengegroup.domain.ChallengeCategory;
import org.haedal.zzansuni.challengegroup.domain.ChallengeGroup;
import org.haedal.zzansuni.userchallenge.domain.application.ChallengeGroupRankingModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChallengeGroupReader {
    ChallengeGroup getById(Long challengeGroupId);

    ChallengeGroup getByIdWithChallenges(Long challengeGroupId);

    Page<ChallengeGroup> getChallengeGroupsPagingByCategory(Pageable pageable, ChallengeCategory category);

    Page<ChallengeGroup> getChallengeGroupsShortsPaging(Pageable pageable, Long userId);

    ChallengeGroupRankingModel.Main getRanking(Long challengeGroupId, Long userId);
}
