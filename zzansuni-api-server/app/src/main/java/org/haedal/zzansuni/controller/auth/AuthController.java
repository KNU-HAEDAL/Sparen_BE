package org.haedal.zzansuni.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.controller.user.UserRes;
import org.haedal.zzansuni.core.api.ApiResponse;
import org.haedal.zzansuni.domain.auth.AuthService;
import org.haedal.zzansuni.domain.user.UserModel;
import org.haedal.zzansuni.global.jwt.JwtToken;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "auth", description = "인증 관련 API")
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "oauth2 로그인", description = "oauth2 code를 이용하여 로그인한다.")
    @PostMapping("/api/auth/oauth2")
    public ApiResponse<AuthRes.LoginResponse> oauth2(@RequestBody @Valid AuthReq.OAuth2LoginRequest request) {
        Pair<JwtToken,UserModel> pair = authService.oAuth2LoginOrSignup(request.provider(), request.code(), request.state());
        var response = AuthRes.LoginResponse.from(pair.getFirst(), pair.getSecond());
        return ApiResponse.success(response);
    }

    @Operation(summary = "이메일 회원가입", description = "이메일 회원가입을 한다.")
    @PostMapping("/api/auth/signup")
    public ApiResponse<AuthRes.LoginResponse> signup(@RequestBody @Valid AuthReq.EmailSignupRequest request) {
        Pair<JwtToken, UserModel> pair = authService.signup(request.toCommand());
        var response = AuthRes.LoginResponse.from(pair.getFirst(), pair.getSecond());
        return ApiResponse.success(response);
    }

    @Operation(summary = "로그인", description = "로그인한다.")
    @PostMapping("/api/auth/login")
    public ApiResponse<AuthRes.LoginResponse> login(@RequestBody @Valid AuthReq.EmailLoginRequest request) {
        Pair<JwtToken, UserModel> pair = authService.login(request.email(), request.password());
        var response = AuthRes.LoginResponse.from(pair.getFirst(), pair.getSecond());
        return ApiResponse.success(response);
    }

    @Operation(summary = "로그아웃", description = "로그아웃한다.")
    @PostMapping("/api/auth/logout")
    public ApiResponse<Void> logout() {
        return ApiResponse.success(null);
    }
}
