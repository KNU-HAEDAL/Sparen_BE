package org.haedal.zzansuni.controller.challengegroup;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.controller.PagingRequest;
import org.haedal.zzansuni.controller.PagingResponse;
import org.haedal.zzansuni.core.api.ApiResponse;
import org.haedal.zzansuni.domain.challengegroup.ChallengeCategory;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroupModel;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroupQueryService;
import org.haedal.zzansuni.global.jwt.JwtUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "challengeGroups", description = "챌린지 그룹 관련 API")
@RequiredArgsConstructor
@RestController
public class ChallengeGroupController {
    private final ChallengeGroupQueryService challengeGroupQueryService;

    @Operation(summary = "챌린지 그룹 목록 페이징", description = "챌린지 그룹 페이징 조회.")
    @GetMapping("/api/challengeGroups")
    public ApiResponse<PagingResponse<ChallengeGroupRes.ChallengeGroupDto>> getChallengesPaging(
            @Valid PagingRequest pagingRequest,
            @RequestParam ChallengeCategory category
    ) {
        var page = challengeGroupQueryService.getChallengeGroupsPaging(pagingRequest.toPageable(), category);
        var response = PagingResponse.from(page, ChallengeGroupRes.ChallengeGroupDto::from);
        return ApiResponse.success(response);
    }

    @Operation(summary = "챌린지 그룹 상세 조회", description = "챌린지 상세 조회한다.")
    @GetMapping("/api/challengeGroups/{challengeGroupId}")
    public ApiResponse<ChallengeGroupRes.ChallengeGroupDetailDto> getChallengeDetail(
            @PathVariable Long challengeGroupId
    ) {
        ChallengeGroupModel.Detail challengeGroupDetail = challengeGroupQueryService.getChallengeGroupDetail(challengeGroupId);
        var response = ChallengeGroupRes.ChallengeGroupDetailDto.from(challengeGroupDetail);
        return ApiResponse.success(response);
    }



    @Operation(summary = "챌린지 그룹 랭킹 조회", description = "챌린지 랭킹 조회한다.")
    @GetMapping("/api/challengeGroups/{challengeGroupId}/rankings")
    public ApiResponse<ChallengeGroupRes.ChallengeGroupRankingPagingResponse> getChallengeRankings(
            @AuthenticationPrincipal JwtUser jwtUser,
            @PathVariable Long challengeGroupId,
            @Valid PagingRequest pagingRequest
    ) {
        var rankingPage = challengeGroupQueryService.getChallengeGroupRankingsPaging(challengeGroupId, pagingRequest.toPageable());
        var rankingModel = challengeGroupQueryService.getChallengeGroupRanking(challengeGroupId, jwtUser.getId());
        var response = ChallengeGroupRes.ChallengeGroupRankingPagingResponse
                .from(rankingPage, rankingModel);
        return ApiResponse.success(response);
    }

    //숏폼 조회
    @Operation(summary = "챌린지 그룹 숏폼 페이징", description = "챌린지 숏폼 페이징 조회한다.")
    @GetMapping("/api/challengeGroups/shorts")
    public ApiResponse<PagingResponse<ChallengeGroupRes.ChallengeGroupDto>> getChallengeShortsPaging(
            @Valid PagingRequest pagingRequest,
            @AuthenticationPrincipal JwtUser jwtUser
    ) {
        var page = challengeGroupQueryService.getChallengeGroupsShortsPaging(pagingRequest.toPageable(), jwtUser.getId());
        var response = PagingResponse.from(page, ChallengeGroupRes.ChallengeGroupDto::from);

        return ApiResponse.success(response);

    }


}
