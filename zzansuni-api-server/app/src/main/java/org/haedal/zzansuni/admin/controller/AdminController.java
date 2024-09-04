package org.haedal.zzansuni.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.auth.controller.AuthReq;
import org.haedal.zzansuni.auth.domain.AuthService;
import org.haedal.zzansuni.challengegroup.domain.application.ChallengeGroupService;
import org.haedal.zzansuni.core.api.ApiResponse;
import org.haedal.zzansuni.user.domain.UserModel;
import org.haedal.zzansuni.user.domain.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "admin", description = "관리자 API")
@RequiredArgsConstructor
@RestController
public class AdminController {

    private final AuthService authService;
    private final ChallengeGroupService challengeGroupService;
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "매니저 등록", description = "매니저를 등록한다.")
    @PostMapping("/api/admin/auth/manager")
    public ApiResponse<Void> createManager(@RequestBody @Valid AuthReq.EmailSignupRequest request) {
        authService.createManager(request.toCommand());
        return ApiResponse.success(null, "매니저 등록 성공");
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "매니저, 어드민 계정 조회", description = "매니저, 어드민 계정을 조회한다.")
    @GetMapping("/api/admin/auth/manager")
    public ApiResponse<List<AdminRes.ManagerAndAdmin>> getAdminAndManager() {
        List<UserModel.Admin> managerAndAdmin = userService.getManagerAndAdmin();
        return ApiResponse.success(AdminRes.ManagerAndAdmin.from(managerAndAdmin), "매니저, 어드민 계정 조회 성공");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "챌린지 그룹 생성", description = "챌린지 그룹과 해당하는 챌린지를 생성합니다")
    @PostMapping("/api/admin/challengeGroups")
    public ApiResponse<Void> createChallengeGroup(
        @RequestBody @Valid AdminReq.CreateChallengeGroupRequest request) {
        challengeGroupService.createChallengeGroup(request.toCommand());
        return ApiResponse.success(null, "챌린지 그룹 생성 성공");
    }

    @Operation(summary = "챌린지 그룹 삭제", description = "챌린지 그룹을 삭제합니다")
    @DeleteMapping("/api/admin/challengeGroups/{challengeGroupId}")
    public ApiResponse<Void> deleteChallengeGroup(@PathVariable Long challengeGroupId) {
        challengeGroupService.deleteChallengeGroup(challengeGroupId);
        return ApiResponse.success(null, "챌린지 그룹 삭제 성공");
    }
}
