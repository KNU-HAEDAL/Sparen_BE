package org.haedal.zzansuni.domain.auth;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.user.User;
import org.haedal.zzansuni.domain.user.UserCommand;
import org.haedal.zzansuni.domain.user.UserModel;
import org.haedal.zzansuni.domain.user.UserReader;
import org.haedal.zzansuni.global.jwt.JwtToken;
import org.haedal.zzansuni.global.jwt.JwtUser;
import org.haedal.zzansuni.global.jwt.JwtUtils;
import org.springframework.data.util.Pair;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final List<OAuth2Client> oAuth2Clients;
    private final JwtUtils jwtUtils;
    private final UserReader userReader;

    /**
     * OAuth2 로그인 또는 회원가입 <br>
     * [state]는 nullable한 입력 값이다.<br>
     * 1. OAuth2Client를 이용해 해당 provider로부터 유저정보를 가져옴
     * 2. authToken으로 유저를 찾거나 없으면 회원가입
     * 3. 토큰 발급, 유저정보 반환
     */
    public Pair<JwtToken, UserModel> oAuth2LoginOrSignup(OAuth2Provider provider, @NonNull String code, @Nullable String state) {
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
        return User.create(command);
    }

    private JwtToken createToken(User user) {
        JwtUser jwtUser = JwtUser.of(user.getId(), user.getRole());
        return jwtUtils.createToken(jwtUser);
    }

}
