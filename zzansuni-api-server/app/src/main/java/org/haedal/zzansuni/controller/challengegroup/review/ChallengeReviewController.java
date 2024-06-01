package org.haedal.zzansuni.controller.challengegroup.review;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.controller.PagingRequest;
import org.haedal.zzansuni.controller.PagingResponse;
import org.haedal.zzansuni.controller.challengegroup.ChallengeGroupRes;
import org.haedal.zzansuni.controller.challengegroup.challenge.ChallengeReq;
import org.haedal.zzansuni.controller.user.UserRes;
import org.haedal.zzansuni.core.api.ApiResponse;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeService;
import org.haedal.zzansuni.domain.challengegroup.review.ChallengeReviewModel.ChallengeReviewWithChallenge;
import org.haedal.zzansuni.global.jwt.JwtUser;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "challengeReviews", description = "챌린지 리뷰 관련 API")
@RestController
@RequiredArgsConstructor
public class ChallengeReviewController {

    private final ChallengeService challengeService;


    @Operation(summary = "챌린지 그룹 최근 리뷰 페이징", description = "챌린지 최근 리뷰 페이징 조회.")
    @GetMapping("/api/challengeGroups/reviews")
    public ApiResponse<PagingResponse<ChallengeReviewRes.ChallengeReviewDto>> getChallengeReviews(
        @Valid PagingRequest pagingRequest
    ) {
        return ApiResponse.success(
            PagingResponse.<ChallengeReviewRes.ChallengeReviewDto>builder()
                .hasNext(false)
                .totalPage(1)
                .data(List.of(
                    new ChallengeReviewRes.ChallengeReviewDto(
                        1L, "title",
                        new UserRes.UserDto(
                            1L, "nickname", "https://picsum.photos/200/300",
                            new UserRes.TierInfoDto(
                                "tier", 100, 50
                            )),
                        "content", 12

                    )
                ))
                .build()
        );
    }


    @Operation(summary = "챌린지 그룹 리뷰 페이징", description = "챌린지 그룹 하위의 모든 챌린지 리뷰 페이징 조회.")
    @GetMapping("/api/challengeGroups/{challengeGroupId}/reviews")
    public ApiResponse<PagingResponse<ChallengeReviewRes.ChallengeReviewWithChalengeDto>> getChallengeReviewsPaging(
        @PathVariable Long challengeGroupId,
        @Valid PagingRequest pagingRequest
        //TODO SORTING
    ) {
        Page<ChallengeReviewWithChallenge> page = challengeService.getChallengeReviewsByGroupId(
            challengeGroupId, pagingRequest.toPageable());

        PagingResponse<ChallengeReviewRes.ChallengeReviewWithChalengeDto> response = PagingResponse.from(
            page, ChallengeReviewRes.ChallengeReviewWithChalengeDto::from
        );

        return ApiResponse.success(response);

    }

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "챌린지 리뷰 작성", description = "챌린지 리뷰를 작성한다.")
    @PostMapping("/api/challenges/{challengeId}/reviews")
    public ApiResponse<Long> challengeReviewCreate(
        @PathVariable Long challengeId,
        @AuthenticationPrincipal JwtUser jwtUser,
        @RequestBody ChallengeReq.ChallengeReviewCreateRequest request
    ) {
        Long response = challengeService.createReview(request.toCommand(), challengeId,
            jwtUser.getId());
        return ApiResponse.success(response, "챌린지 리뷰 작성에 성공하였습니다.");
    }

}
