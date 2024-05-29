package org.haedal.zzansuni.controller.user;

import jakarta.validation.constraints.NotBlank;
import org.haedal.zzansuni.domain.user.UserCommand;

public class UserReq {
    public record UserUpdateRequest(
            @NotBlank(message = "nickname은 필수입니다.") String nickname
    ) {
        public UserCommand.Update toCommand() {
            return UserCommand.Update.builder()
                    .nickname(nickname)
                    .build();
        }
    }
}
