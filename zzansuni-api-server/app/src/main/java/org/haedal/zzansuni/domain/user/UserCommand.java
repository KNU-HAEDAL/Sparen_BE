package org.haedal.zzansuni.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.haedal.zzansuni.core.utils.SelfValidating;
import org.haedal.zzansuni.domain.auth.OAuth2Provider;

public class UserCommand {
    @Getter
    @Builder
    public static class Create extends SelfValidating<UserCommand.Create> {
        @Email(message = "이메일 형식은 유효하여야 합니다.")
        private final String email;
        @NotBlank(message = "password는 필수입니다.")
        private String password;
        @NotBlank(message = "닉네임은 필수입니다.")
        private final String nickname;

        public Create(String email, String password, String nickname) {
            this.email = email;
            this.password = password;
            this.nickname = nickname;
            this.validateSelf();
        }

        public void changePassword(String encodedPassword){
            this.password = encodedPassword;
        }
    }

    @Getter
    @Builder
    public static class CreateOAuth2 extends SelfValidating<UserCommand.CreateOAuth2> {
        private final String authToken;
        private final String nickname;
        @NotNull(message = "OAuth2Provider는 필수입니다.")
        private final OAuth2Provider provider;

        public CreateOAuth2(String authToken, String nickname, OAuth2Provider provider) {
            this.authToken = authToken;
            this.nickname = nickname;
            this.provider = provider;
            if(authToken.isBlank() || nickname.isBlank()){
                throw new RuntimeException("authToken, nickname은 필수입니다.");
            }
            this.validateSelf();
        }
    }

    @Getter
    @Builder
    public static class Update extends SelfValidating<UserCommand.Update> {
        @NotBlank(message = "닉네임은 필수입니다.")
        private final String nickname;

        public Update(String nickname) {
            this.nickname = nickname;
            this.validateSelf();
        }
    }
}
