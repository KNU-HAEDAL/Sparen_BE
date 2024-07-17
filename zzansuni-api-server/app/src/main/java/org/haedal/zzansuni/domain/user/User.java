package org.haedal.zzansuni.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.haedal.zzansuni.domain.BaseTimeEntity;
import org.haedal.zzansuni.domain.auth.OAuth2Provider;
import org.haedal.zzansuni.global.security.Role;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(nullable = false)
    private Integer exp;

    @Enumerated(EnumType.STRING)
    private OAuth2Provider provider;

    private String authToken;

    private String profileImageUrl;

    public static User create(UserCommand.CreateOAuth2 command) {
        return User.builder()
            .nickname(command.getNickname())
            .email(null)
            .password(null)
            .role(Role.USER)
            .provider(command.getProvider())
            .authToken(command.getAuthToken())
            .exp(0)
            .profileImageUrl(null)
            .build();
    }

    public static User create(UserCommand.Create command) {
        if (!command.isValid()) {
            throw new IllegalStateException("인코딩에 실패하였습니다.");
        }
        return User.builder()
            .nickname(command.getNickname())
            .email(command.getEmail())
            .password(command.getEncodedPassword())
            .role(Role.USER)
            .provider(null)
            .authToken(null)
            .exp(0)
            .profileImageUrl(null)
            .build();
    }

    public static User createManager(UserCommand.Create command) {
        if (!command.isValid()) {
            throw new IllegalStateException("인코딩에 실패하였습니다.");
        }
        return User.builder()
            .nickname(command.getNickname())
            .email(command.getEmail())
            .password(command.getEncodedPassword())
            .role(Role.MANAGER)
            .provider(null)
            .authToken(null)
            .exp(0)
            .profileImageUrl(null)
            .build();
    }

    public void update(UserCommand.Update userUpdate) {
        this.nickname = userUpdate.getNickname();
    }

    public void addExp(Integer exp) {
        this.exp += exp;
    }
}
