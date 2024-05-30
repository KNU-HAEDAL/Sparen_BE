package org.haedal.zzansuni.domain.challengegroup.challenge;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ChallengeStatus {
    PROCEEDING("진행중"),
    FINISHED("종료");

    private final String korean;
}
