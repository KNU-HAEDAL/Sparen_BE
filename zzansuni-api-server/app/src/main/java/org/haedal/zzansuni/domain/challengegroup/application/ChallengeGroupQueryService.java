package org.haedal.zzansuni.domain.challengegroup.application;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.challengegroup.ChallengeCategory;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroup;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroupImage;
import org.haedal.zzansuni.domain.challengegroup.port.ChallengeGroupImageReader;
import org.haedal.zzansuni.domain.challengegroup.port.ChallengeGroupReader;
import org.haedal.zzansuni.domain.userchallenge.ChallengeGroupUserExp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public Page<ChallengeGroupModel.Ranking> getChallengeGroupRankingsPaging(Long challengeGroupId, Pageable pageable) {
        Page<ChallengeGroupUserExp> challengeGroupUserExps
                = challengeGroupReader.getUserExpPagingWithUserByChallengeGroupId(challengeGroupId, pageable);

        // Page<ChallengeGroupUserExp>를 Page<ChallengeGroupModel.Ranking>으로 변환
        // [rank]는 [Pageable]의 위치에 따라 계산된다.
        return challengeGroupUserExps.map(e->{
            int rank = challengeGroupUserExps.getNumber() * challengeGroupUserExps.getSize() + 1
                    + challengeGroupUserExps.getContent().indexOf(e);
            return ChallengeGroupModel.Ranking.from(e, rank);
        });
    }


    public ChallengeGroupModel.Ranking getChallengeGroupRanking(Long challengeGroupId, Long id) {
        return challengeGroupReader.getRanking(challengeGroupId, id);
    }


}
