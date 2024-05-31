package org.haedal.zzansuni.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.controller.auth.AuthReq;
import org.haedal.zzansuni.core.api.ApiResponse;
import org.haedal.zzansuni.domain.auth.AuthService;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroupService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "admin", description = "관리자 API")
@RequiredArgsConstructor
@RestController
public class AdminController {
    private final AuthService authService;
    private final ChallengeGroupService challengeGroupService;

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary= "매니저 등록", description = "매니저를 등록한다.")
    @PostMapping("/api/auth/manager")
    public ApiResponse<Void> createManager(@RequestBody @Valid AuthReq.EmailSignupRequest request) {
        authService.createManager(request.toCommand());
        return ApiResponse.success(null, "매니저 등록 성공");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "챌린지 그룹 생성", description = "챌린지 그룹과 해당하는 챌린지를 생성합니다")
    public ApiResponse<Void> createChallengeGroup(@RequestBody @Valid AdminReq.CreateChallengeGroupRequest request) {
        challengeGroupService.createChallengeGroup(request.toCommand());
        return ApiResponse.success(null, "챌린지 그룹 생성 성공");
    }
}
