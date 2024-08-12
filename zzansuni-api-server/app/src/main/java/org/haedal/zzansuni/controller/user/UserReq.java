package org.haedal.zzansuni.controller.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.haedal.zzansuni.domain.user.UserCommand;
import org.springdoc.core.annotations.ParameterObject;

import java.time.LocalDate;

public class UserReq {
    public record Update(
            @NotBlank(message = "nickname은 필수입니다.") String nickname
    ) {
        public UserCommand.Update toCommand() {
            return UserCommand.Update.builder()
                    .nickname(nickname)
                    .build();
        }
    }

    @ParameterObject
    public record GetStrick(
            @Schema(description = "시작일(null이면 종료일보다 365일 전)", example = "2023-08-12")
            LocalDate startDate,
            @Schema(description = "종료일(null이면 현재일)", example = "2024-08-12")
            LocalDate endDate
    ){
        public GetStrick{
            if(endDate == null){
                endDate = LocalDate.now();
            }
            if(startDate == null){
                startDate = endDate.minusDays(365);
            }
        }
    }
}
