package org.haedal.zzansuni.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.auth.controller.AuthReq;
import org.haedal.zzansuni.auth.domain.AuthService;
import org.haedal.zzansuni.challengegroup.controller.ChallengeGroupReq;
import org.haedal.zzansuni.challengegroup.domain.application.ChallengeGroupService;
import org.haedal.zzansuni.common.controller.PagingRequest;
import org.haedal.zzansuni.common.controller.PagingResponse;
import org.haedal.zzansuni.common.domain.ImageUploader;
import org.haedal.zzansuni.core.api.ApiResponse;
import org.haedal.zzansuni.user.domain.UserModel;
import org.haedal.zzansuni.user.domain.UserService;
import org.haedal.zzansuni.userchallenge.controller.ChallengeRes;
import org.haedal.zzansuni.userchallenge.domain.ChallengeVerificationStatus;
import org.haedal.zzansuni.userchallenge.domain.application.ChallengeVerificationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "admin", description = "관리자 API")
@RequiredArgsConstructor
@RestController
public class AdminController {

    private final AuthService authService;
    private final ChallengeGroupService challengeGroupService;
    private final UserService userService;
    private final ChallengeVerificationService challengeVerificationService;
    private final ImageUploader imageUploader;

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
        List<UserModel.Main> managerAndAdmin = userService.getManagerAndAdmin();
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

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "챌린지 그룹 수정", description = "챌린지 그룹을 수정합니다. 새로운 챌린지를 추가할땐 id=-1로 설정합니다.")
    @PutMapping("/api/admin/challengeGroups/{challengeGroupId}")
    public ApiResponse<Void> updateChallengeGroup(@Valid @RequestBody ChallengeGroupReq.Update request) {
        challengeGroupService.updateChallengeGroup(request.toCommand());
        return ApiResponse.success(null, "챌린지 그룹 수정 성공");
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "챌린지 인증 조회", description = "챌린지 인증을 페이징 조회합니다. 챌린지 이름으로 검색이 가능합니다.")
    @GetMapping("/api/admin/challenges/verifications")
    public ApiResponse<PagingResponse<ChallengeRes.ChallengeVerification>> getChallengeVerifications(
            @Valid PagingRequest pagingRequest,
            @RequestParam(required = false) String challengeTitle){
        var verificationPage = challengeVerificationService.getChallengeVerifications(pagingRequest.toPageable(), challengeTitle);
        return ApiResponse.success(PagingResponse.from(verificationPage, ChallengeRes.ChallengeVerification::from), "챌린지 인증 조회 성공");
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "챌린지 인증 승인/거절", description = "챌린지 인증을 승인/거절합니다. 거절시 경험치가 취소됩니다.")
    @PatchMapping("/api/admin/challenges/verifications/{challengeVerificationId}")
    public ApiResponse<Void> approveChallengeVerification(@PathVariable Long challengeVerificationId,
                                                          @Valid @RequestParam ChallengeVerificationStatus status) {
        challengeVerificationService.confirm(challengeVerificationId, status);
        return ApiResponse.success(null, "챌린지 인증 승인/거절 성공");
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "챌린지 그룹 이미지 등록", description = "챌린지 그룹 이미지를 등록 UPSERT")
    @PostMapping("/api/admin/challengeGroups/{challengeGroupId}/image")
    public ApiResponse<Void> createChallengeGroupImage(@PathVariable Long challengeGroupId,
                                                       List<MultipartFile> images) {
        List<String> imageUrls = images.stream()
                .map(imageUploader::upload)
                .toList();
        challengeGroupService.updateChallengeGroupImages(challengeGroupId, imageUrls);
        return ApiResponse.success(null, "챌린지 그룹 이미지 등록 성공");
    }

}
