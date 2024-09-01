package org.haedal.zzansuni.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * 티어 정보를 enum으로 정적으로 관리
 * 향후 데이터베이스에 저장할수도 있음
 */
@Getter
@RequiredArgsConstructor
public enum TierSystem {
    NOBI_4("노비 IV", 0, 5),
    NOBI_3("노비 III", 5, 10),
    NOBI_2("노비 II", 10, 20),
    NOBI_1("노비 I", 20, 40),
    SANGMIN_4("상민 IV", 40, 60),
    SANGMIN_3("상민 III", 60, 80),
    SANGMIN_2("상민 II", 80, 100),
    SANGMIN_1("상민 I", 100, 120),
    PYEONGMIN_4("평민 IV", 120, 140),
    PYEONGMIN_3("평민 III", 140, 160),
    PYEONGMIN_2("평민 II", 160, 180),
    PYEONGMIN_1("평민 I", 180, 200),
    YANGBAN_4("양반 IV", 200, 230),
    YANGBAN_3("양반 III", 230, 260),
    YANGBAN_2("양반 II", 260, 290),
    YANGBAN_1("양반 I", 290, 320),
    JINGOL_4("진골 IV", 320, 360),
    JINGOL_3("진골 III", 360, 400),
    JINGOL_2("진골 II", 400, 440),
    JINGOL_1("진골 I", 440, 480),
    SEONGOL_4("성골 IV", 480, 530),
    SEONGOL_3("성골 III", 530, 580),
    SEONGOL_2("성골 II", 580, 630),
    SEONGOL_1("성골 I", 630, 680),
    ECHO_4("에코 IV", 680, 740),
    ECHO_3("에코 III", 740, 800),
    ECHO_2("에코 II", 800, 860),
    ECHO_1("에코 I", 860, Integer.MAX_VALUE);


    private final String korean;
    private final int startExp;
    private final int endExp;

    public static TierSystem getTier(int exp) {
        return Arrays.stream(TierSystem.values())
                .filter(tier -> tier.startExp <= exp && exp < tier.endExp)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("티어를 찾을 수 없습니다."));

    }
}
