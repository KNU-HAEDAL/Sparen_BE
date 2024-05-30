package org.haedal.zzansuni.controller.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.haedal.zzansuni.domain.auth.OAuth2Provider;

public class AuthReq {
    public record OAuth2LoginRequest(
            @NotNull(message = "provider는 필수입니다.")
            OAuth2Provider provider,
            @NotBlank(message = "code는 필수입니다.")
            String code,
            String state
    ) {
    }
}
