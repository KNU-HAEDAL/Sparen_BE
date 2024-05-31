package org.haedal.zzansuni.controller.challengegroup.challenge;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.haedal.zzansuni.controller.PagingRequest;
import org.haedal.zzansuni.controller.PagingResponse;
import org.haedal.zzansuni.core.api.ApiResponse;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeService;
import org.haedal.zzansuni.domain.challengegroup.userchallenge.UserChallengeCommand;
import org.haedal.zzansuni.domain.challengegroup.userchallenge.UserChallengeService;
import org.haedal.zzansuni.global.jwt.JwtUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "challenge", description = "챌린지 API")
@RequiredArgsConstructor
@RestController
@Slf4j
public class ChallengeController {

    private final ChallengeService challengeService;
    private final UserChallengeService userChallengeService;

    @Operation(summary = "챌린지 참여", description = "챌린지에 참여한다.")
    @PostMapping("/api/challenges/{challengeId}/join")
    public ApiResponse<Void> challengeParticipation(
        @PathVariable Long challengeId,
        @AuthenticationPrincipal JwtUser jwtUser
    ) {
        UserChallengeCommand.Participate command = new UserChallengeCommand.Participate(challengeId,
            jwtUser.getId());
        userChallengeService.participateChallenge(jwtUser.getId(), command);
        return ApiResponse.success(null, "챌린지 참여에 성공하였습니다.");
    }

    @Operation(summary = "챌린지 인증", description = "챌린지에 인증한다.")
    @PostMapping("/api/challenges/{challengeId}/verification")
    public ApiResponse<ChallengeRes.ChallengeVerificationResponse> challengeVerification(
        @PathVariable Long challengeId,
        @RequestPart("body") ChallengeReq.ChallengeVerificationRequest request,
        @RequestPart("image") MultipartFile image
    ) {
        throw new RuntimeException("Not implemented");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "챌린지 리뷰 작성", description = "챌린지 리뷰를 작성한다.")
    @PostMapping("/api/challenges/{challengeId}/reviews")
    public ApiResponse<Long> challengeReviewCreate(
        @PathVariable Long challengeId,
        @AuthenticationPrincipal JwtUser jwtUser,
        @RequestBody ChallengeReq.ChallengeReviewCreateRequest request
    ) {
        throw new RuntimeException("Not implemented");
    }

    @Operation(summary = "챌린지 기록 조회", description = "챌린지 기록을 조회한다.")
    @GetMapping("/api/challenges/{challengeId}/record")
    public ApiResponse<ChallengeRes.ChallengeRecordResponse> getChallengeRecord(
        @PathVariable Long challengeId,
        @AuthenticationPrincipal JwtUser jwtUser
    ) {
        throw new RuntimeException("Not implemented");
    }

    @Operation(summary = "챌린지 기록 상세 조회", description = "챌린지 기록 상세를 조회한다.")
    @GetMapping("/api/challenges/record/{recordId}")
    public ApiResponse<ChallengeRes.ChallengeRecordDetailDto> getChallengeRecordDetail(
        @PathVariable Long recordId,
        @AuthenticationPrincipal JwtUser jwtUser
    ) {
        throw new RuntimeException("Not implemented");
    }

    @Operation(summary = "진행중인 챌린지 조회", description = "진행중인 챌린지 조회한다.")
    @GetMapping("/api/user/challenges/currents")
    public ApiResponse<PagingResponse<ChallengeRes.ChallengeCurrentDto>> getChallengeCurrentsPaging(
        @Valid PagingRequest pagingRequest,
        @AuthenticationPrincipal JwtUser jwtUser
    ) {
        throw new RuntimeException("Not implemented");
    }

    @Operation(summary = "완료한 챌린지 조회", description = "완료한 챌린지 페이징 조회한다.")
    @GetMapping("/api/user/challenges/completes")
    public ApiResponse<PagingResponse<ChallengeRes.ChallengeCompleteDto>> getChallengeCompletesPaging(
        @Valid PagingRequest pagingRequest,
        @AuthenticationPrincipal JwtUser jwtUser
    ) {
        throw new RuntimeException("Not implemented");
    }
}
