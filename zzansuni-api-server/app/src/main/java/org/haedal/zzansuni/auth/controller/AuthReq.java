package org.haedal.zzansuni.auth.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.haedal.zzansuni.auth.domain.OAuth2Provider;
import org.haedal.zzansuni.user.domain.UserCommand;

public class AuthReq {
    public record OAuth2LoginRequest(
            @NotNull(message = "provider는 필수입니다.")
            OAuth2Provider provider,
            @NotBlank(message = "code는 필수입니다.")
            String code,
            String state
    ) {
    }

    public record EmailSignupRequest(
            @NotBlank(message = "email은 필수입니다.")
            String email,
            @NotBlank(message = "password는 필수입니다.")
            String password,
            @NotBlank(message = "nickname은 필수입니다.")
            String nickname
    ) {
        public UserCommand.Create toCommand() {
            return UserCommand.Create.builder()
                    .email(email)
                    .password(password)
                    .nickname(nickname)
                    .build();
        }
    }

    public record EmailLoginRequest(
            @NotBlank(message = "email은 필수입니다.")
            @Schema(description = "이메일", example = "test@a.c")
            String email,
            @NotBlank(message = "password는 필수입니다.")
            @Schema(description = "비밀번호", example = "test")
            String password
    ) {
    }
}
