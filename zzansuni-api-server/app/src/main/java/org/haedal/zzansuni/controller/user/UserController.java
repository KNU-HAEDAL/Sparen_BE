package org.haedal.zzansuni.controller.user;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.controller.PagingResponse;
import org.haedal.zzansuni.core.api.ApiResponse;
import org.haedal.zzansuni.domain.user.UserService;
import org.haedal.zzansuni.global.jwt.JwtUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Tag(name = "user", description = "유저 API")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @Operation(summary = "내 정보 조회", description = "내 정보를 조회한다.")
    @GetMapping("/api/user")
    public ApiResponse<UserRes.UserInfoDto> getUserInfo(
            @AuthenticationPrincipal JwtUser jwtUser
    ) {
        return ApiResponse.success(
                new UserRes.UserInfoDto(1L, "nickname", "profileImageUrl", "email",
                        new UserRes.TierInfoDto("tier", 100, 50)
                )
        );
    }

    @Operation(summary = "내 정보 수정", description = "내 정보를 수정한다.")
    @PutMapping("/api/user")
    public ApiResponse<Void> updateUser(
            @Valid @RequestBody UserReq.UserUpdateRequest request,
            @AuthenticationPrincipal JwtUser jwtUser
    ) {
        var command = request.toCommand();
        userService.updateUser(jwtUser.getId(), command);
        return ApiResponse.success(null, "수정에 성공하였습니다.");
    }

    @Operation(summary = "스트릭 조회", description = "스트릭을 조회한다.")
    @GetMapping("/api/user/strick")
    public ApiResponse<List<UserRes.StrickDto>> getStrick(
            @AuthenticationPrincipal JwtUser jwtUser,
            @RequestParam(required = false) LocalDate startDate, // false면 오늘
            @RequestParam(required = false) LocalDate endDate // false면 365일전
    ) {
        return ApiResponse.success(List.of(
                new UserRes.StrickDto(LocalDate.now(), List.of(
                        new UserRes.DayCountDto(LocalDate.now(), 1)
                ))
        ));
    }

    @Operation(summary = "유저 랭킹 페이징", description = "전체 유저 랭킹을 조회 페이징")
    @GetMapping("/api/users/ranking")
    public ApiResponse<PagingResponse<Void>> getUsersRanking(
            @Valid @RequestParam Long page
    ) {
        // TODO
        return ApiResponse.success(null);
    }



}
