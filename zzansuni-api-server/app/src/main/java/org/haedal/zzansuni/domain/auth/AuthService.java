package org.haedal.zzansuni.domain.auth;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.user.*;
import org.haedal.zzansuni.global.jwt.JwtToken;
import org.haedal.zzansuni.global.jwt.JwtUser;
import org.haedal.zzansuni.global.jwt.JwtUtils;
import org.springframework.data.util.Pair;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final List<OAuth2Client> oAuth2Clients;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final UserReader userReader;
    private final UserStore userStore;

    /**
     * OAuth2 로그인 또는 회원가입 <br> [state]는 nullable한 입력 값이다.<br> 1. OAuth2Client를 이용해 해당 provider로부터
     * 유저정보를 가져옴 2. authToken으로 유저를 찾거나 없으면 회원가입 3. 토큰 발급, 유저정보 반환
     */
    public Pair<JwtToken, UserModel> oAuth2LoginOrSignup(OAuth2Provider provider,
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
        JwtToken jwtToken = createToken(user);
        UserModel userModel = UserModel.from(user);
        return Pair.of(jwtToken, userModel);
    }


    private User signup(OAuthUserInfoModel oAuthUserInfoModel, OAuth2Provider provider) {
        UserCommand.CreateOAuth2 command = oAuthUserInfoModel.toCreateCommand(provider);
        User user = User.create(command);
        return userStore.store(user);
    }

    private JwtToken createToken(User user) {
        JwtUser jwtUser = JwtUser.of(user.getId(), user.getRole());
        return jwtUtils.createToken(jwtUser);
    }

    @Transactional
    public Pair<JwtToken, UserModel> signup(UserCommand.Create command) {
        if (userReader.existsByEmail(command.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        command = command.copyEncodedPassword(passwordEncoder.encode(command.getPassword()));
        User user = User.create(command);
        userStore.store(user);
        JwtToken jwtToken = createToken(user);
        UserModel userModel = UserModel.from(user);
        return Pair.of(jwtToken, userModel);
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

    @Transactional(readOnly = true)
    public Pair<JwtToken, UserModel> login(String email, String password) {
        User user = userReader.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        JwtToken jwtToken = createToken(user);
        UserModel userModel = UserModel.from(user);
        return Pair.of(jwtToken, userModel);
    }

    public String reissueToken(String rawToken) {
        if (!jwtUtils.validateToken(rawToken)) {
            throw new IllegalArgumentException("RefreshToken이 유효하지 않습니다.");
        }
        JwtToken.ValidToken token = JwtToken.ValidToken.of(rawToken);
        jwtUtils.reissueAccessToken(token);

        return jwtUtils.reissueAccessToken(JwtToken.ValidToken.of(rawToken));
    }
}
