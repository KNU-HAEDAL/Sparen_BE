package org.haedal.zzansuni.domain.user;

import lombok.Builder;
import lombok.Getter;

@Builder
public record UserModel(
    Long id,
    String email,
    String nickname,
    String profileImageUrl,
    Integer exp
) {
    public static UserModel from(User user) {
        return UserModel.builder()
            .id(user.getId())
            .email(user.getEmail())
            .nickname(user.getNickname())
            .profileImageUrl(user.getProfileImageUrl())
            .exp(user.getExp())
            .build();
    }
}
