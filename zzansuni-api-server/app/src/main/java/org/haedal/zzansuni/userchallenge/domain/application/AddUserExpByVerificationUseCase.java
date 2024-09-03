package org.haedal.zzansuni.userchallenge.domain.application;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.userchallenge.domain.port.ChallengeGroupUserExpReader;
import org.haedal.zzansuni.userchallenge.domain.ChallengeGroupUserExp;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AddUserExpByVerificationUseCase {
    private final ChallengeGroupUserExpReader challengeGroupUserExpReader;

    @Transactional
    public void invoke(AddUserExpByVerificationEvent event) {
        Long challengeGroupId = event.getChallengeGroupId();
        Long userId = event.getUserId();

        ChallengeGroupUserExp challengeGroupUserExp = challengeGroupUserExpReader
                .findByChallengeGroupIdAndUserId(challengeGroupId, userId).orElseThrow();
        challengeGroupUserExp.addExp(event.getAcquiredExp());
    }
}
