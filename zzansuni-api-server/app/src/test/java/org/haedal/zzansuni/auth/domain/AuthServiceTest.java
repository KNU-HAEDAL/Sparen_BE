package org.haedal.zzansuni.auth.domain;

import jakarta.persistence.EntityManager;
import org.haedal.zzansuni.user.domain.User;
import org.haedal.zzansuni.user.domain.UserModel;
import org.haedal.zzansuni.global.jwt.JwtToken;
import org.haedal.zzansuni.user.infrastructure.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.util.Pair;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Import(AuthServiceTest.AuthServiceTestConfig.class)
class AuthServiceTest {
    @Autowired private AuthService authService;
    @Autowired private UserRepository userRepository;
    @Autowired private EntityManager em;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }


    @DisplayName("OAuth2 회원가입이 정상적으로 동작한다.")
    @Test
    void oAuth2LoginOrSignup() {
        // given
        String code = "fake-random-code";

        // when
        Pair<JwtToken, UserModel.Main> pair = authService.oAuth2LoginOrSignup(OAuth2Provider.KAKAO, code, null);

        // then
        User user = userRepository.findAll().get(0);

        assertThat(user.getNickname()).isEqualTo("fake-nickname"+code);
        assertThat(user.getAuthToken()).isEqualTo("FAKE"+code);
        assertThat(pair.getFirst().getAccessToken()).isNotNull();
        assertThat(pair.getSecond().id()).isEqualTo(user.getId());
    }

    @DisplayName("이미 가입된 유저가 로그인을 시도할경우, 로그인이 정상적으로 동작한다.")
    @Test
    void oAuth2LoginOrSignupWhenAlreadySignedUp() {
        // given
        String code = "fake-random";
        authService.oAuth2LoginOrSignup(OAuth2Provider.KAKAO, code, null);
        em.clear();

        // when
        authService.oAuth2LoginOrSignup(OAuth2Provider.KAKAO, code, null);

        // then
        assertThat(userRepository.count()).isEqualTo(1);
    }



    @TestConfiguration
    static class AuthServiceTestConfig {
        @Bean
        @Primary
        public OAuth2Client fakeOAuth2Client() {
            return new FakeOAuth2Client();
        }

    }

    static class FakeOAuth2Client implements OAuth2Client {

        @Override
        public boolean canHandle(OAuth2Provider provider) {
            return true;
        }

        @Override
        public OAuthUserInfoModel getAuthToken(String code, String state) {
            return OAuthUserInfoModel.builder()
                    .authToken("FAKE"+code)
                    .nickname("fake-nickname"+code)
                    .build();
        }
    }
}
