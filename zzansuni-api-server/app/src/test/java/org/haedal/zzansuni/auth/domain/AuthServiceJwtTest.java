package org.haedal.zzansuni.auth.domain;

import org.haedal.zzansuni.auth.infrastructure.RefreshTokenRepository;
import org.haedal.zzansuni.common.domain.UuidHolder;
import org.haedal.zzansuni.global.jwt.JwtToken;
import org.haedal.zzansuni.user.domain.UserCommand;
import org.haedal.zzansuni.user.domain.UserModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.util.Pair;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Import(AuthServiceJwtTest.AuthServiceJwtTestConfig.class)
public class AuthServiceJwtTest {
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthServiceJwtTestConfig.TestUuidHolder testUuidHolder;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;


    @Test
    @DisplayName("회원가입시 리프레시 토큰이 저장된다.")
    public void loginTest() {
        // given
        testUuidHolder.setFixedUuid("test-uuid");
        UserCommand.Create create = UserCommand.Create.builder()
                .email("test@a.c")
                .password("test")
                .nickname("test")
                .build();

        // when
        authService.signup(create);

        // then
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findById("test-uuid");
        assertThat(refreshToken).isPresent();
    }

    @Test
    @DisplayName("토큰 재발급시, 기존 리프레시 토큰이 삭제된다.")
    public void refreshTest() {
        // given
        testUuidHolder.setFixedUuid("test-uuid1");
        UserCommand.Create create = UserCommand.Create.builder()
                .email("test@a.c")
                .password("test")
                .nickname("test")
                .build();
        Pair<JwtToken, UserModel.Main> jwtAndUser = authService.signup(create);

        // when
        testUuidHolder.setFixedUuid("test-uuid2");
        authService.reissueToken(jwtAndUser.getFirst().getRefreshToken());

        // then
        Optional<RefreshToken> removedToken = refreshTokenRepository.findById("test-uuid");
        Optional<RefreshToken> reissuedToken = refreshTokenRepository.findById("test-uuid2");
        assertAll(
                () -> assertThat(removedToken).isEmpty(),
                () -> assertThat(reissuedToken).isPresent()
        );
    }

    @Test
    @DisplayName("만료된 토큰으로 재발급 요청시, 예외가 발생한다.")
    public void refreshWithExpiredToken() {
        // given
        testUuidHolder.setFixedUuid("test-uuid4");
        UserCommand.Create create = UserCommand.Create.builder()
                .email("test@a.c")
                .password("test")
                .nickname("test")
                .build();
        Pair<JwtToken, UserModel.Main> jwtAndUser = authService.signup(create);
        String refreshToken = jwtAndUser.getFirst().getRefreshToken();

        // 재발급 진행
        testUuidHolder.setFixedUuid("test-uuid5");
        authService.reissueToken(refreshToken);

        // when & then
        testUuidHolder.setFixedUuid("test-uuid6");
        assertThatThrownBy(() -> authService.reissueToken(refreshToken))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("uuid가 충돌되는 경우, RETRY가 정상적으로 진행된다.")
    public void refreshWithConflictUuid() {
        // given
        testUuidHolder.changeThreeFixedAndRandomMode();
        UserCommand.Create create1 = UserCommand.Create.builder()
                .email("te121st@a.c")
                .password("test")
                .nickname("test")
                .build();
        Pair<JwtToken, UserModel.Main> jwtAndUser1 = authService.signup(create1);

        UserCommand.Create create = UserCommand.Create.builder()
                .email("test@a.c")
                .password("test")
                .nickname("test")
                .build();

        // when & then
        authService.signup(create);
    }


    @TestConfiguration
    public static class AuthServiceJwtTestConfig {
        @Bean
        @Primary
        public TestUuidHolder fixedUuidHolder() {
            return new TestUuidHolder();
        }

        public static class TestUuidHolder implements UuidHolder {
            private enum HolderMode {
                FIXED, RANDOM, THREE_FIXED_AND_RANDOM
            }

            private HolderMode mode = HolderMode.FIXED;
            private String fixedUuid = "test-uuid";
            private int count = 0;

            @Override
            public String random() {
                return switch (mode) {
                    case FIXED -> fixedUuid;
                    case RANDOM -> UUID.randomUUID().toString();
                    case THREE_FIXED_AND_RANDOM -> {
                        if (count < 3) {
                            count++;
                            yield fixedUuid;
                        }
                        yield UUID.randomUUID().toString();
                    }
                };
            }

            public void changeRandomMode() {
                mode = HolderMode.RANDOM;
                count = 0;
            }
            public void setFixedUuid(String uuid) {
                mode = HolderMode.FIXED;
                fixedUuid = uuid;
                count = 0;
            }
            public void changeThreeFixedAndRandomMode() {
                mode = HolderMode.THREE_FIXED_AND_RANDOM;
                count = 0;
            }
        }

    }
}
