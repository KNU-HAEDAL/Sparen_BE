package org.haedal.zzansuni.domain.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.haedal.zzansuni.core.utils.SelfValidating;

public class UserCommand {

    @Getter
    @Builder
    public static class Update extends SelfValidating<UserCommand.Update> {
        @NotBlank(message = "닉네임은 필수입니다.")
        private String nickname;

        public Update(String nickname) {
            this.nickname = nickname;
            this.validateSelf();
        }
    }
}
