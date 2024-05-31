package org.haedal.zzansuni.infrastructure.auth;


import org.haedal.zzansuni.domain.auth.OAuth2Client;
import org.haedal.zzansuni.domain.auth.OAuth2Provider;
import org.haedal.zzansuni.domain.auth.OAuthUserInfoModel;
import org.haedal.zzansuni.global.exception.ExternalServerConnectionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Component
@Profile({"prod", "dev"})
public class NaverOAuth2Client implements OAuth2Client {
    private final AuthTokenGenerator authTokenGenerator;
    private final RestClient restClient;
    public NaverOAuth2Client(AuthTokenGenerator authTokenGenerator, ClientHttpRequestFactory clientHttpRequestFactory) {
        this.authTokenGenerator = authTokenGenerator;
        // 타임아웃 설정, 4xx, 5xx 에러 핸들러 설정
        this.restClient = RestClient
                .builder()
                .requestFactory(clientHttpRequestFactory)
                .defaultStatusHandler(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new IllegalStateException("네이버 인증 code 요청에 실패했습니다.");
                })
                .defaultStatusHandler(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new ExternalServerConnectionException("네이버 인증 서버로 부터 문제가 발생했습니다.");
                })
                .build();
    }
    private static final String GRANT_TYPE = "authorization_code";
    @Value("${naver.client-id}")
    private String clientId;
    @Value("${naver.redirect-uri}")
    private String redirectUri;
    @Value("${naver.client-secret}")
    private String clientSecret;

    @Override
    public boolean canHandle(OAuth2Provider provider) {
        return OAuth2Provider.NAVER.equals(provider);
    }

    @Override
    public OAuthUserInfoModel getAuthToken(String code, String state) {
        if(state == null) throw new IllegalArgumentException("state 값이 없습니다.");
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);
        body.add("client_secret", clientSecret);
        body.add("state", state);

        // 네이버 API를 통해 토큰을 발급받음
        NaverTokenResponse tokenResponse = restClient
                .post()
                .uri("https://nid.naver.com/oauth2.0/token")
                .body(body)
                .retrieve()
                .body(NaverTokenResponse.class);
        String token = tokenResponse.accessToken();

        // 네이버 API를 통해 사용자 정보를 가져옴
        NaverUserInfoResponse userInfoResponse = restClient
                .get()
                .uri("https://openapi.naver.com/v1/nid/me")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(NaverUserInfoResponse.class);
        String id = userInfoResponse.response().id();
        String nickname = userInfoResponse.response().nickname();

        // id와 nickname을 이용하여 authToken을 생성
        String authToken = authTokenGenerator.generate(id, OAuth2Provider.NAVER);
        return OAuthUserInfoModel.builder()
                .nickname(nickname)
                .authToken(authToken)
                .build();
    }
}
