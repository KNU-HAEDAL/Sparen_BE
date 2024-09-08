package org.haedal.zzansuni.userchallenge.domain.application;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.userchallenge.domain.ChallengeVerification;
import org.haedal.zzansuni.userchallenge.domain.port.ChallengeVerificationReader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChallengeVerificationService {
    private final ChallengeVerificationReader challengeVerificationReader;


    @Transactional(readOnly = true)
    public Page<ChallengeVerificationModel.Admin> getChallengeVerifications(Pageable pageable,
                                                                            String challengeTitle) {
        Page<ChallengeVerification> verifications = challengeVerificationReader.getVerificationOrderByCreatedAt(pageable, challengeTitle);
        return verifications.map(ChallengeVerificationModel.Admin::from);
    }
}
