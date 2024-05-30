package org.haedal.zzansuni.controller.challenge.interact;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.controller.PagingResponse;
import org.haedal.zzansuni.core.api.ApiResponse;
import org.haedal.zzansuni.global.jwt.JwtUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "challenge-interact", description = "챌린지 상호작용 API")
@RequiredArgsConstructor
@RestController
public class ChallengeInteractController {

    @Operation(summary = "챌린지 참여", description = "챌린지에 참여한다.")
    @PostMapping("/api/challenges/{challengeId}/join")
    public ApiResponse<Void> challengeParticipation(
            @PathVariable Long challengeId,
            @AuthenticationPrincipal JwtUser jwtUser
    ) {
        throw new RuntimeException("Not implemented");
    }

    @Operation(summary = "챌린지 인증", description = "챌린지에 인증한다.")
    @PostMapping("/api/challenges/{challengeId}/verification")
    public ApiResponse<ChallengeInteractRes.ChallengeVerificationResponse> challengeVerification(
            @PathVariable Long challengeId,
            @RequestPart("body") ChallengeInteractReq.ChallengeVerificationRequest request,
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
            @RequestBody ChallengeInteractReq.ChallengeReviewCreateRequest request
    ) {
        throw new RuntimeException("Not implemented");
    }

    @Operation(summary = "챌린지 기록 조회", description = "챌린지 기록을 조회한다.")
    @GetMapping("/api/challenges/{challengeId}/record")
    public ApiResponse<ChallengeInteractRes.ChallengeRecordResponse> getChallengeRecord(
            @PathVariable Long challengeId,
            @AuthenticationPrincipal JwtUser jwtUser
    ) {
        throw new RuntimeException("Not implemented");
    }

    @Operation(summary = "챌린지 기록 상세 조회", description = "챌린지 기록 상세를 조회한다.")
    @GetMapping("/api/challenges/record/{recordId}")
    public ApiResponse<ChallengeInteractRes.ChallengeRecordDetailDto> getChallengeRecordDetail(
            @PathVariable Long recordId,
            @AuthenticationPrincipal JwtUser jwtUser
    ) {
        throw new RuntimeException("Not implemented");
    }

}
