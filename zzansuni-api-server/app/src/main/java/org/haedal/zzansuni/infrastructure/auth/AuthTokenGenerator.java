package org.haedal.zzansuni.infrastructure.auth;

import org.haedal.zzansuni.domain.auth.OAuth2Provider;
import org.springframework.stereotype.Component;

/**
 * 인증 토큰을 생성하는 클래스
 * OAuth2Client를 상속하는 클래스에서 공통으로 사용되는 로직을 분리하였다.
 */
@Component
public class AuthTokenGenerator {
    public String generate(String token, OAuth2Provider provider) {
        return provider.name() + "_" + token;
    }
}
