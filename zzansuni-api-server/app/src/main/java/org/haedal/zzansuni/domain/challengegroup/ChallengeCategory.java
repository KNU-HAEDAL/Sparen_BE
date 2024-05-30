package org.haedal.zzansuni.domain.challengegroup;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ChallengeCategory {
    HEALTH("건강"),
    ECHO("에코"),

    SHARE("나눔"),

    VOLUNTEER("봉사"),
    ETC("기타");

    private final String korean;
}
