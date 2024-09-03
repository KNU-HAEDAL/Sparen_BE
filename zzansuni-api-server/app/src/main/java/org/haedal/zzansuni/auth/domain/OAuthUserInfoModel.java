package org.haedal.zzansuni.auth.domain;

import lombok.Builder;
import org.haedal.zzansuni.user.domain.UserCommand;

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
