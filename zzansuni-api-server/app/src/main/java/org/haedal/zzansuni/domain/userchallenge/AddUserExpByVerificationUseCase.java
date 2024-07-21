package org.haedal.zzansuni.domain.userchallenge;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.challengegroup.port.ChallengeGroupReader;
import org.haedal.zzansuni.domain.userchallenge.userexp.ChallengeGroupUserExp;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AddUserExpByVerificationUseCase {
    private final ChallengeGroupReader challengeGroupReader;

    @Transactional
    public void invoke(AddUserExpByVerificationEvent event) {
        Long challengeGroupId = event.getChallengeGroupId();
        Long userId = event.getUserId();

        ChallengeGroupUserExp challengeGroupUserExp = challengeGroupReader
                .findByChallengeGroupIdAndUserId(challengeGroupId, userId).orElseThrow();
        challengeGroupUserExp.addExp(event.getAcquiredExp());
    }
}
