package org.haedal.zzansuni.auth.domain;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface OAuth2Client {
    boolean canHandle(OAuth2Provider provider);

    /**
     * 인증코드를 이용하여 사용자 정보를 가져온다.
     * [state]가 필요한 Client의 경우 해당 파라미터를 사용한다.
     */
    OAuthUserInfoModel getAuthToken(@NonNull String code, @Nullable String state);
}
