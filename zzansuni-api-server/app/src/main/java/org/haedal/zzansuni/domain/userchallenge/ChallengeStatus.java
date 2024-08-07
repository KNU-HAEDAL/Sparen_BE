package org.haedal.zzansuni.domain.userchallenge;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ChallengeStatus {
    PROCEEDING("진행중"),
    SUCCESS("성공"),
    FAIL("실패");

    public boolean isProceeding() {
        return this == PROCEEDING;
    }

    private final String korean;
}
