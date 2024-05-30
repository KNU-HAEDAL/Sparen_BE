package org.haedal.zzansuni.domain.auth;

import lombok.Builder;
import org.haedal.zzansuni.domain.user.UserCommand;

@Builder
public record OAuthUserInfoModel(
        String authToken,
        String nickname
) {
    public UserCommand.CreateOAuth2 toCreateCommand(OAuth2Provider provider){
        return UserCommand.CreateOAuth2
                .builder()
                .nickname(nickname)
                .authToken(authToken)
                .provider(provider)
                .build();
    }
}
