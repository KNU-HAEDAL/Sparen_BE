package org.haedal.zzansuni.user.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.haedal.zzansuni.core.utils.SelfValidating;
import org.haedal.zzansuni.auth.domain.OAuth2Provider;

public class UserCommand {

    @Getter
    public static class Create extends SelfValidating<UserCommand.Create> {

        @Email(message = "이메일 형식은 유효하여야 합니다.")
        private final String email;
        @NotBlank(message = "password는 필수입니다.")
        private final String password;
        @NotBlank(message = "닉네임은 필수입니다.")
        private final String nickname;

        private final String encodedPassword;

        @Builder
        public Create(String email, String password, String nickname) {
            this.email = email;
            this.password = password;
            this.nickname = nickname;
            this.encodedPassword = null;

            this.validateSelf();
        }

        private Create(String email, String password, String nickname, String encodedPassword) {
            this.email = email;
            this.password = password;
            this.nickname = nickname;
            this.encodedPassword = encodedPassword;
        }

        public UserCommand.Create copyEncodedPassword(String encodedPassword) {
            return new Create(email, null, nickname, encodedPassword);
        }

        public boolean isValid() {
            return password == null && encodedPassword != null;
        }


    }

    @Getter
    public static class CreateOAuth2 extends SelfValidating<UserCommand.CreateOAuth2> {

        private final String authToken;
        private final String nickname;
        @NotNull(message = "OAuth2Provider는 필수입니다.")
        private final OAuth2Provider provider;

        @Builder
        public CreateOAuth2(String authToken, String nickname, OAuth2Provider provider) {
            this.authToken = authToken;
            this.nickname = nickname;
            this.provider = provider;
            if (authToken.isBlank() || nickname.isBlank()) {
                throw new RuntimeException("authToken, nickname은 필수입니다.");
            }
            this.validateSelf();
        }
    }

    @Getter
    public static class Update extends SelfValidating<UserCommand.Update> {

        @NotBlank(message = "닉네임은 필수입니다.")
        private final String nickname;

        @Builder
        public Update(String nickname) {
            this.nickname = nickname;
            this.validateSelf();
        }
    }
}
