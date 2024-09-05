package org.haedal.zzansuni.auth.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.haedal.zzansuni.global.jwt.JwtToken;
import org.haedal.zzansuni.global.jwt.JwtUtils;
import org.haedal.zzansuni.user.domain.*;
import org.haedal.zzansuni.user.domain.port.UserReader;
import org.haedal.zzansuni.user.domain.port.UserStore;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.util.Pair;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final List<OAuth2Client> oAuth2Clients;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final UserReader userReader;
    private final UserStore userStore;
    private final CreateJwtUseCase createJwtUseCase;

    /**
     * OAuth2 로그인 또는 회원가입 <br>
     * [state]는 nullable한 입력 값이다.<br>
     * 1. OAuth2Client를 이용해 해당 provider로부터 유저정보를 가져옴
     * 2. authToken으로 유저를 찾거나 없으면 회원가입
     * 3. 토큰 발급, 유저정보 반환
     */
    public Pair<JwtToken, UserModel.Main> oAuth2LoginOrSignup(OAuth2Provider provider,
                                                              @NonNull String code, @Nullable String state) {
        OAuth2Client oAuth2Client = oAuth2Clients.stream()
                .filter(client -> client.canHandle(provider))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 OAuth2Provider 입니다."));

        // OAuth2Client를 이용해 해당 provider로부터 유저정보를 가져옴
        OAuthUserInfoModel oAuthUserInfoModel = oAuth2Client.getAuthToken(code, state);

        // authToken으로 유저를 찾아서 없으면 [OAuthUserInfoModel]를 통해서 회원가입 진행
        User user = userReader
                .findByAuthToken(oAuthUserInfoModel.authToken())
                .orElseGet(() -> signup(oAuthUserInfoModel, provider));

        // 토큰 발급, 유저정보 반환
        JwtToken jwtToken = createJwtToken(user);
        UserModel.Main userMain = UserModel.Main.from(user);
        return Pair.of(jwtToken, userMain);
    }


    private User signup(OAuthUserInfoModel oAuthUserInfoModel, OAuth2Provider provider) {
        UserCommand.CreateOAuth2 command = oAuthUserInfoModel.toCreateCommand(provider);
        User user = User.create(command);
        return userStore.store(user);
    }

    public Pair<JwtToken, UserModel.Main> signup(UserCommand.Create command) {
        if (userReader.existsByEmail(command.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        command = command.copyEncodedPassword(passwordEncoder.encode(command.getPassword()));
        User user = User.create(command);
        userStore.store(user);
        JwtToken jwtToken = createJwtToken(user);

        UserModel.Main userMain = UserModel.Main.from(user);
        return Pair.of(jwtToken, userMain);
    }

    @Transactional
    public void createManager(UserCommand.Create command) {
        if (userReader.existsByEmail(command.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        command = command.copyEncodedPassword(passwordEncoder.encode(command.getPassword()));
        User user = User.createManager(command);
        userStore.store(user);
    }

    public Pair<JwtToken, UserModel.Main> login(String email, String password) {
        User user = userReader.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        JwtToken jwtToken = createJwtToken(user);
        UserModel.Main userMain = UserModel.Main.from(user);
        return Pair.of(jwtToken, userMain);
    }

    public JwtToken reissueToken(String rawToken) {
        JwtUtils.UserIdAndUuid userIdAndUuid = jwtUtils.validateAndGetUserIdAndUuid(rawToken);

        for(int i = 0; i < 5; i++) {
            try {
                return createJwtUseCase.removeRefreshTokenAndCreateJwt(userIdAndUuid);
            } catch (DataIntegrityViolationException e) {
                log.error("중복된 uuid 발생, 재시도  : {}", i);
            }
        }
        throw new RuntimeException("로그인 처리중에 문제가 발생하였습니다. 잠시 후 다시 시도해주세요.");
    }

    /**
     * 중복 uuid 저장이 발생하는 경우를 대비하여 10번까지 시도한다.
     * 유저 생성과 리프래시토큰 저장을 한 트랜잭션에서 처리하게 된다면
     * 모든 처리가 롤백되어야 한다. <br>
     * 데이터베이스에서 발생한 에러는 트랜잭션 상태에 영향을 미쳐 예외가 발생한 후의 추가 처리에 실패한다.
     * `Transaction`과 관련된 AOP에서 `noRollbackFor`에서의 에러로 처리가 불가능하다.
     */
    private JwtToken createJwtToken(User user) {
        for(int i = 0; i < 5; i++) {
            try {
                return createJwtUseCase.invoke(user);
            } catch (DataIntegrityViolationException e) {
                log.error("중복된 uuid 발생, 재시도  : {}", i);
            }
        }
        throw new RuntimeException("로그인 처리중에 문제가 발생하였습니다. 잠시 후 다시 시도해주세요.");
    }
}
