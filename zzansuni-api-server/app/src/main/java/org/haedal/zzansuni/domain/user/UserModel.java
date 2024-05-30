package org.haedal.zzansuni.domain.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserModel {
    private Long id;
    private String email;
    private String nickname;
    private String profileImageUrl;
    private Integer exp;

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
