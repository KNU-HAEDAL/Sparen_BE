package org.haedal.zzansuni.domain.challengegroup;

import lombok.RequiredArgsConstructor;
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

    public Page<ChallengeGroupModel.Ranking> getChallengeGroupRankingsPaging(Long challengeGroupId, Pageable pageable) {
        ChallengeGroup challengeGroup = challengeGroupReader.getById(challengeGroupId);
        throw new UnsupportedOperationException("Not implemented yet");

    }

    public ChallengeGroupModel.Ranking getChallengeGroupRanking(Long challengeGroupId, Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
