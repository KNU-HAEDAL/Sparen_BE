package org.haedal.zzansuni.userchallenge.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ChallengeVerificationStatus {
    PRE_APPROVED("예비승인"),
    APPROVED("승인"),
    REJECTED("거절");

    private final String korean;
}
