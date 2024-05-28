package org.haedal.zzansuni.controller.challenge;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.controller.PagingRequest;
import org.haedal.zzansuni.controller.PagingResponse;
import org.haedal.zzansuni.core.api.ApiResponse;
import org.haedal.zzansuni.global.jwt.JwtUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "challenges", description = "챌린지 관련 API")
@RequiredArgsConstructor
@RestController
public class ChallengeController {

    @Operation(summary = "챌린지 목록 조회", description = "챌린지 목록 페이징 조회한다.")
    @GetMapping("/api/challenges")
    public ApiResponse<PagingResponse<ChallengeRes.ChallengeDto>> getChallengesPaging(
            @Valid PagingRequest pagingRequest
    ) {
        throw new RuntimeException("Not implemented");
    }

    @Operation(summary = "챌린지 상세 조회", description = "챌린지 상세 조회한다.")
    @GetMapping("/api/challenges/{challengeId}")
    public ApiResponse<ChallengeRes.ChallengeDetailDto> getChallengeDetail(
            @PathVariable Long challengeId
    ) {
        throw new RuntimeException("Not implemented");
    }

    @Operation(summary = "챌린지 최근 리뷰 조회", description = "챌린지 최근 리뷰 조회한다.")
    @GetMapping("/api/challenges/reviews")
    public ApiResponse<PagingResponse<ChallengeRes.ChallengeReviewDto>> getChallengeReviews(
            @Valid PagingRequest pagingRequest
    ) {
        throw new RuntimeException("Not implemented");
    }

    @Operation(summary = "챌린지 랭킹 조회", description = "챌린지 랭킹 조회한다.")
    @GetMapping("/api/challenges/{challengeId}/rankings")
    public ApiResponse<PagingResponse<ChallengeRes.ChallengeRankingPagingResponse>> getChallengeRankings(
            @AuthenticationPrincipal JwtUser jwtUser,
            @PathVariable Long challengeId,
            @Valid PagingRequest pagingRequest
    ) {
        throw new RuntimeException("Not implemented");
    }

    //숏폼 조회
    @Operation(summary = "챌린지 숏폼 조회", description = "챌린지 숏폼 조회한다.")
    @GetMapping("/api/challenges/shorts")
    public ApiResponse<PagingResponse<ChallengeRes.ChallengeDto>> getChallengeShorts(
            @RequestParam Long page
    ) {
        throw new RuntimeException("Not implemented");
    }

}
