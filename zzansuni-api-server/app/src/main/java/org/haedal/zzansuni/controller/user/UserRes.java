package org.haedal.zzansuni.controller.user;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class UserRes {
    @Builder
    public record UserInfoDto(
            Long id,
            String nickname,
            String profileImageUrl,
            String email,
            TierInfoDto tierInfo
    ) {
    }

    @Builder
    public record UserDto(
            Long id,
            String nickname,
            String profileImageUrl,
            TierInfoDto tierInfo
    ) {
    }

    @Builder
    public record TierInfoDto(
            String tier,
            Integer totalExp,
            Integer currentExp
    ) {
    }

    @Builder
    public record StrickDto(
            LocalDate date,
            List<DayCountDto> dayCounts
    ) {
    }

    /**
     * 하루에 대한 스트릭 카운트
     * 0인 것도 보낸다.
     */
    @Builder
    public record DayCountDto(
            LocalDate date,
            Integer count
    ) {
    }

}
