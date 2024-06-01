package org.haedal.zzansuni.domain.challengegroup.challenge;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ChallengeStatus {
    PROCEEDING("진행중"),
    SUCCESS("성공"),
    FAIL("실패");

    private final String korean;
}
