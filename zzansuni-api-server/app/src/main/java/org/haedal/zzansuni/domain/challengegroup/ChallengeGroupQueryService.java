package org.haedal.zzansuni.domain.challengegroup;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.challengegroup.image.ChallengeGroupImage;
import org.haedal.zzansuni.domain.challengegroup.image.ChallengeGroupImageReader;
import org.haedal.zzansuni.domain.challengegroup.userexp.ChallengeGroupUserExp;
import org.haedal.zzansuni.domain.user.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeGroupQueryService {
    private final ChallengeGroupReader challengeGroupReader;
    private final ChallengeGroupImageReader challengeGroupImageReader;

    @Transactional(readOnly = true)
    public Page<ChallengeGroupModel.Info> getChallengeGroupsPaging(Pageable pageable, ChallengeCategory category) {
        Page<ChallengeGroup> challengeGroups = challengeGroupReader.getChallengeGroupsPagingByCategory(pageable, category);
        return challengeGroups.map(ChallengeGroupModel.Info::from);
    }

    @Transactional(readOnly = true)
    public ChallengeGroupModel.Detail getChallengeGroupDetail(Long challengeGroupId) {
        ChallengeGroup challengeGroup = challengeGroupReader.getByIdWithChallenges(challengeGroupId);
        List<ChallengeGroupImage> challengeGroupImages = challengeGroupImageReader.getByChallengeGroupId(challengeGroupId);
        return ChallengeGroupModel.Detail.from(challengeGroup, challengeGroupImages);
    }

    @Transactional(readOnly = true)
    public Page<ChallengeGroupModel.Info> getChallengeGroupsShortsPaging(Pageable pageable, Long userId) {
        Page<ChallengeGroup> challengeGroups = challengeGroupReader.getChallengeGroupsShortsPaging(pageable, userId);
        return challengeGroups.map(ChallengeGroupModel.Info::from);
    }

    public Page<ChallengeGroupModel.Ranking> getChallengeGroupRankingsPaging(Long challengeGroupId, Pageable pageable) {
        Page<ChallengeGroupUserExp> challengeGroupUserExps
                = challengeGroupReader.getByChallengeGroupId(challengeGroupId, pageable);

        return getRankingPage(pageable, challengeGroupUserExps);
    }

    private static PageImpl<ChallengeGroupModel.Ranking> getRankingPage(Pageable pageable, Page<ChallengeGroupUserExp> challengeGroupUserExps) {
        List<ChallengeGroupModel.Ranking> rankings = new ArrayList<>();
        for(int i = 0; i < challengeGroupUserExps.getContent().size(); i++) {
            Integer rank = challengeGroupUserExps.getNumber() * challengeGroupUserExps.getSize() + 1 + i;
            ChallengeGroupUserExp challengeGroupUserExp = challengeGroupUserExps.getContent().get(i);
            var rankingModel = ChallengeGroupModel.Ranking.builder()
                    .user(UserModel.from(challengeGroupUserExp.getUser()))
                    .accumulatedPoint(challengeGroupUserExp.getTotalExp())
                    .rank(rank)
                    .build();
            rankings.add(rankingModel);
        }
        return new PageImpl<>(rankings, pageable, challengeGroupUserExps.getTotalElements());
    }

    public ChallengeGroupModel.Ranking getChallengeGroupRanking(Long challengeGroupId, Long id) {
        return challengeGroupReader.getRanking(challengeGroupId, id);
    }


}
