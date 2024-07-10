package org.haedal.zzansuni.controller.user;

import lombok.Builder;
import org.haedal.zzansuni.domain.user.TierSystem;
import org.haedal.zzansuni.domain.user.UserModel;

import java.time.LocalDate;
import java.util.List;

public class UserRes {
    @Builder
    public record UserInfo(
            Long id,
            String nickname,
            String profileImageUrl,
            String email,
            TierInfo tierInfo
    ) {
        public static UserInfo from(UserModel.Main userMain) {
            var tierInfo = TierInfo.from(userMain.exp());
            return UserInfo.builder()
                    .id(userMain.id())
                    .nickname(userMain.nickname())
                    .profileImageUrl(userMain.profileImageUrl())
                    .email(userMain.email())
                    .tierInfo(tierInfo)
                    .build();
        }
    }

    @Builder
    public record User(
            Long id,
            String nickname,
            String profileImageUrl,
            TierInfo tierInfo
    ) {
        public static User from(UserModel.Main userMain) {
            var tierInfo = TierInfo.from(userMain.exp());
            return User.builder()
                    .id(userMain.id())
                    .nickname(userMain.nickname())
                    .profileImageUrl(userMain.profileImageUrl())
                    .tierInfo(tierInfo)
                    .build();
        }
    }

    @Builder
    public record TierInfo(
            String tier,
            Integer totalExp,
            Integer currentExp
    ) {
        public static TierInfo from(Integer exp) {
            var tier = TierSystem.getTier(exp);

            return TierInfo.builder()
                    .tier(tier.getKorean())
                    .totalExp(tier.getEndExp() - tier.getStartExp()) // 티어 시작 경험치부터 끝 경험치까지
                    .currentExp(exp - tier.getStartExp()) // 현재 경험치 - 티어 시작 경험치
                    .build();
        }
    }

    @Builder
    public record Strick(
            /** 여기서 Model의 DayCount를 사용해도 되는지 */
            List<UserModel.DayCount> dayCounts
    ) {
        public static Strick from(UserModel.Strick strick){
            return Strick.builder()
                    .dayCounts(strick.dayCounts())
                    .build();
        }
    }

    /**
     * 하루에 대한 스트릭 카운트
     * 0인 것도 보낸다.
     */
    @Builder
    public record DayCount(
            LocalDate date,
            Integer count
    ) {
    }

}
