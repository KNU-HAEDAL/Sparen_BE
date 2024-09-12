package org.haedal.zzansuni.userchallenge.domain.application;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.challengegroup.domain.Challenge;
import org.haedal.zzansuni.user.domain.User;
import org.haedal.zzansuni.userchallenge.domain.ChallengeVerification;
import org.haedal.zzansuni.userchallenge.domain.ChallengeVerificationStatus;
import org.haedal.zzansuni.userchallenge.domain.UserChallenge;
import org.haedal.zzansuni.userchallenge.domain.port.ChallengeVerificationReader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChallengeVerificationService {
    private final ChallengeVerificationReader challengeVerificationReader;
    private final SubUserExpByVerificationUseCase subUserExpByVerificationUseCase;


    @Transactional(readOnly = true)
    public Page<ChallengeVerificationModel.Admin> getChallengeVerifications(Pageable pageable,
                                                                            String challengeTitle) {
        Page<ChallengeVerification> verifications = challengeVerificationReader.getVerificationOrderByCreatedAt(pageable, challengeTitle);
        return verifications.map(ChallengeVerificationModel.Admin::from);
    }

    /**
     * 중복 승인/거절을 방지하기 위해 request와 저장된 데이터의 값이 다를 경우만 진행
     * REJECTED : ChallengeVerificationStatus를 REJECTED로 변경하고, 경험치를 롤백한다.(횟수를 다채워 successEXP를 얻은 경우, successEXP도 롤백한다.)
     * APPROVED : ChallengeVerificationStatus를 APPROVED로 변경한다.
     */
    @Transactional
    public void confirm(Long verificationId, ChallengeVerificationStatus status) {
        ChallengeVerification verification = challengeVerificationReader.getById(verificationId); // verification 가져올때 user, challenge, userChallenge도 join해서 가져오는게 좋을듯
        if (status == ChallengeVerificationStatus.REJECTED && verification.getStatus() != ChallengeVerificationStatus.REJECTED) {
            rejectVerification(verification);
        } else if (status == ChallengeVerificationStatus.APPROVED && verification.getStatus() != ChallengeVerificationStatus.APPROVED) {
            approveVerification(verification);
        }
    }

    private void rejectVerification(ChallengeVerification verification) {
        verification.reject();
        revertUserExp(verification);
    }

    private void approveVerification(ChallengeVerification verification) {
        verification.approve();
    }

    private void revertUserExp(ChallengeVerification verification) {
        User user = verification.getUserChallenge().getUser();
        Challenge challenge = verification.getUserChallenge().getChallenge();
        UserChallenge userChallenge = verification.getUserChallenge();

        Integer revertedExp = challenge.getOnceExp();
        if (userChallenge.getSuccessDate() != null) {
            revertedExp += challenge.getSuccessExp();
        }

        SubUserExpByVerificationEvent event = SubUserExpByVerificationEvent
                .of(user.getId(), revertedExp, challenge.getChallengeGroupId());
        subUserExpByVerificationUseCase.invoke(event);
    }
}
