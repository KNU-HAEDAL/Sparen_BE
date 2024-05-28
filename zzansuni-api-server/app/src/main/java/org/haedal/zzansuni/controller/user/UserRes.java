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
            Long exp,
            TierDto tier
    ) {
    }

    @Builder
    public record TierDto(
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
