package org.haedal.zzansuni.userchallenge.domain.application;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.challengegroup.domain.port.ChallengeGroupReader;
import org.haedal.zzansuni.userchallenge.domain.port.ChallengeGroupUserExpReader;
import org.haedal.zzansuni.userchallenge.domain.ChallengeGroupUserExp;
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
    public Page<ChallengeGroupRankingModel.Main> getChallengeGroupRankingsPaging(Long challengeGroupId, Pageable pageable) {
        Page<ChallengeGroupUserExp> challengeGroupUserExps
                = challengeGroupUserExpReader.getUserExpPagingWithUserByChallengeGroupId(challengeGroupId, pageable);

        // Page<ChallengeGroupUserExp>를 Page<ChallengeGroupModel.Ranking>으로 변환
        // [rank]는 [Pageable]의 위치에 따라 계산된다.
        return challengeGroupUserExps.map(e->{
            int rank = challengeGroupUserExps.getNumber() * challengeGroupUserExps.getSize() + 1
                    + challengeGroupUserExps.getContent().indexOf(e);
            return ChallengeGroupRankingModel.Main.from(e, rank);
        });
    }


    public ChallengeGroupRankingModel.Main getChallengeGroupRanking(Long challengeGroupId, Long id) {
        return challengeGroupReader.getRanking(challengeGroupId, id);
    }
}
