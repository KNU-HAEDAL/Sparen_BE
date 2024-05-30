package org.haedal.zzansuni.infrastructure.auth;

import org.haedal.zzansuni.domain.auth.OAuth2Client;
import org.haedal.zzansuni.domain.auth.OAuth2Provider;
import org.haedal.zzansuni.domain.auth.OAuthUserInfoModel;
import org.haedal.zzansuni.global.exception.ExternalServerConnectionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Component
@Profile({"prod", "dev"})
public class KakaoOAuth2Client implements OAuth2Client {
    private final AuthTokenGenerator authTokenGenerator;
    private final RestClient restClient;

    public KakaoOAuth2Client(AuthTokenGenerator authTokenGenerator, ClientHttpRequestFactory clientHttpRequestFactory) {
        this.authTokenGenerator = authTokenGenerator;
        // 타임아웃 설정, 4xx, 5xx 에러 핸들러 설정
        this.restClient = RestClient
                .builder()
                .requestFactory(clientHttpRequestFactory)
                .defaultStatusHandler(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new IllegalStateException("카카오 인증 code 요청에 실패했습니다.");
                })
                .defaultStatusHandler(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new ExternalServerConnectionException("카카오 인증 서버로 부터 문제가 발생했습니다.");
                })
                .build();
    }


    private static final String GRANT_TYPE = "authorization_code";

    @Value("${kakao.client-id}")
    private String clientId;
    @Value("${kakao.redirect-uri}")
    private String redirectUri;
    @Value("${kakao.client-secret}")
    private String clientSecret;

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
        String authToken = authTokenGenerator.generate(id, OAuth2Provider.KAKAO);
        return OAuthUserInfoModel.builder()
                .nickname(nickname)
                .authToken(authToken)
                .build();

    }
}
