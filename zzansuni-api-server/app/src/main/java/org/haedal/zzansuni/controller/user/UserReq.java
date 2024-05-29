package org.haedal.zzansuni.controller.user;

import org.haedal.zzansuni.domain.user.UserCommand;

public class UserReq {
    public record UserUpdateRequest(String nickname){
        public UserCommand.Update toCommand(){
            return UserCommand.Update.builder()
                    .nickname(nickname)
                    .build();
        }
    }
}
