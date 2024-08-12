package org.haedal.zzansuni.domain.userchallenge.application;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.userchallenge.ChallengeGroupUserExp;
import org.haedal.zzansuni.domain.challengegroup.application.ChallengeGroupModel;
import org.haedal.zzansuni.domain.challengegroup.port.ChallengeGroupReader;
import org.haedal.zzansuni.domain.userchallenge.port.ChallengeGroupUserExpReader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChallengeRankingService {
    private final ChallengeGroupReader challengeGroupReader;
    private final ChallengeGroupUserExpReader challengeGroupUserExpReader;

    @Transactional(readOnly = true)
    public Page<ChallengeGroupModel.Ranking> getChallengeGroupRankingsPaging(Long challengeGroupId, Pageable pageable) {
        Page<ChallengeGroupUserExp> challengeGroupUserExps
                = challengeGroupUserExpReader.getUserExpPagingWithUserByChallengeGroupId(challengeGroupId, pageable);

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
