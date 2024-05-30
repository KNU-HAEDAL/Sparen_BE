package org.haedal.zzansuni.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.controller.user.UserRes;
import org.haedal.zzansuni.core.api.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "auth", description = "인증 관련 API")
@RequiredArgsConstructor
@RestController
public class AuthController {

    @Operation(summary = "oauth2 로그인", description = "oauth2 code를 이용하여 로그인한다.")
    @PostMapping("/api/auth/oauth2")
    public ApiResponse<AuthRes.LoginResponse> oauth2(@RequestBody @Valid AuthReq.OAuth2LoginRequest request) {
        return ApiResponse.success(
                new AuthRes.LoginResponse("accessToken", "refresh", new UserRes.UserInfoDto(
                        1L, "nickname", "profileImageUrl", "email",
                        new UserRes.TierInfoDto("tier", 100, 50)
                )));
    }

    @Operation(summary = "로그아웃", description = "로그아웃한다.")
    @PostMapping("/api/auth/logout")
    public ApiResponse<Void> logout() {
        return ApiResponse.success(null);
    }
}
