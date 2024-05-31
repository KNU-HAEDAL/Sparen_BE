package org.haedal.zzansuni.controller.user;

import lombok.Builder;
import org.haedal.zzansuni.domain.user.TierSystem;
import org.haedal.zzansuni.domain.user.UserModel;

import java.time.LocalDate;
import java.util.List;

public class UserRes {
    @Builder
    public record UserInfoDto(
            Long id,
            String nickname,
            String profileImageUrl,
            String email,
            TierInfoDto tierInfo
    ) {
        public static UserInfoDto from(UserModel userModel) {
            var tierInfo = TierInfoDto.from(userModel.getExp());
            return UserInfoDto.builder()
                    .id(userModel.getId())
                    .nickname(userModel.getNickname())
                    .profileImageUrl(userModel.getProfileImageUrl())
                    .email(userModel.getEmail())
                    .tierInfo(tierInfo)
                    .build();
        }
    }

    @Builder
    public record UserDto(
            Long id,
            String nickname,
            String profileImageUrl,
            TierInfoDto tierInfo
    ) {
        public static UserDto from(UserModel userModel) {
            var tierInfo = TierInfoDto.from(userModel.getExp());
            return UserDto.builder()
                    .id(userModel.getId())
                    .nickname(userModel.getNickname())
                    .profileImageUrl(userModel.getProfileImageUrl())
                    .tierInfo(tierInfo)
                    .build();
        }
    }

    @Builder
    public record TierInfoDto(
            String tier,
            Integer totalExp,
            Integer currentExp
    ) {
        public static TierInfoDto from(Integer exp) {
            var tier = TierSystem.getTier(exp);

            return TierInfoDto.builder()
                    .tier(tier.getKorean())
                    .totalExp(tier.getEndExp() - tier.getStartExp()) // 티어 시작 경험치부터 끝 경험치까지
                    .currentExp(exp - tier.getStartExp()) // 현재 경험치 - 티어 시작 경험치
                    .build();
        }
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
