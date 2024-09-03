package org.haedal.zzansuni.user.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.common.controller.PagingRequest;
import org.haedal.zzansuni.common.controller.PagingResponse;
import org.haedal.zzansuni.core.api.ApiResponse;
import org.haedal.zzansuni.user.domain.UserService;
import org.haedal.zzansuni.global.jwt.JwtUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "user", description = "유저 API")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @Operation(summary = "내 정보 조회", description = "내 정보를 조회한다.")
    @GetMapping("/api/user")
    public ApiResponse<UserRes.UserInfo> getUserInfo(
            @AuthenticationPrincipal JwtUser jwtUser
    ) {
        var userModel = userService.getUserModel(jwtUser.getId());
        return ApiResponse.success(UserRes.UserInfo.from(userModel));
    }

    @Operation(summary = "내 정보 수정", description = "내 정보를 수정한다.")
    @PutMapping("/api/user")
    public ApiResponse<Void> updateUser(
            @Valid @RequestBody UserReq.Update request,
            @AuthenticationPrincipal JwtUser jwtUser
    ) {
        var command = request.toCommand();
        userService.updateUser(jwtUser.getId(), command);
        return ApiResponse.success(null, "수정에 성공하였습니다.");
    }

    @Operation(summary = "스트릭 조회", description = "스트릭을 조회한다.")
    @GetMapping("/api/user/strick")
    public ApiResponse<UserRes.Strick> getStrick(
            @AuthenticationPrincipal JwtUser jwtUser,
            UserReq.GetStrick request
    ) {
        if(request.startDate().isAfter(request.endDate())){
            throw new IllegalArgumentException("시작일은 종료일보다 이전이어야 합니다.");
        }
        var userModelStrick = userService.getUserStrick(
                jwtUser.getId(),
                request.startDate(),
                request.endDate()
        );
        return ApiResponse.success(UserRes.Strick.from(userModelStrick));
    }

    @Operation(summary = "유저 랭킹 페이징", description = "전체 유저 랭킹을 조회 페이징")
    @GetMapping("/api/users/ranking")
    public ApiResponse<PagingResponse<UserRes.UserInfo>> getUsersRanking(
            @Valid PagingRequest request
    ) {
        var userModelPage = userService.getUserPagingByRanking(request.toPageable());
        var response = PagingResponse.from(userModelPage, UserRes.UserInfo::from);
        return ApiResponse.success(response);
    }


}