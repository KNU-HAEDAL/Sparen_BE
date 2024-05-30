package org.haedal.zzansuni.infrastructure.auth;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.auth.OAuth2Client;
import org.haedal.zzansuni.domain.auth.OAuth2Provider;
import org.haedal.zzansuni.domain.auth.OAuthUserInfoModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
@Component
public class KakaoOAuth2Client implements OAuth2Client {
    private final RestClient restClient = RestClient.create();
    private static final String GRANT_TYPE = "authorization_code";

    @Value("${kakao.client-id}") private String clientId;
    @Value("${kakao.redirect-uri}") private String redirectUri;
    @Value("${kakao.client-secret}") private String clientSecret;

    @Override
    public boolean canHandle(OAuth2Provider provider) {
        return OAuth2Provider.KAKAO.equals(provider);
    }

    /**
     * 카카오 OAuth2 인증을 통해 사용자 id를 추출하여 authToken을 생성한다.
     * 1. 카카오 OAuth2 인증을 통해 토큰을 발급받는다.
     * 2. 토큰을 이용하여 사용자 정보를 조회한다.
     * 3. 사용자 정보에서 id와 nickname을 추출하여 OAuthUserInfoModel을 반환한다.
     */
    @Override
    public OAuthUserInfoModel getAuthToken(@NonNull String code, @Nullable String state) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);
        body.add("client_secret", clientSecret);


        KakaoTokenResponse tokenResponse = restClient
                .post()
                .uri("https://kauth.kakao.com/oauth/token")
                .body(body)
                .retrieve()
                .body(KakaoTokenResponse.class);
        String token = tokenResponse.accessToken();

        KakaoUserInfoResponse userInfo = restClient
                .get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(KakaoUserInfoResponse.class);

        String id = userInfo.id().toString();
        String nickname = userInfo.properties().nickname();
        String authToken = OAuth2Provider.KAKAO + "_" + id;
        return OAuthUserInfoModel.builder()
                .nickname(nickname)
                .authToken(authToken)
                .build();

    }
}
