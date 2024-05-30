package org.haedal.zzansuni.controller.user;

import lombok.Builder;

import java.time.LocalDate;
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
            TierInfoDto tier
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
            Map<String, Integer> activity
    ) {
    }

}
