package org.haedal.zzansuni.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.haedal.zzansuni.global.security.Role;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String email;

    private String password;

    @Column(nullable = false)
    private Integer exp;

    private String authToken;

    private String profileImageUrl;

    public void update(UserCommand.Update userUpdate) {
        this.nickname = userUpdate.getNickname();
    }
}
