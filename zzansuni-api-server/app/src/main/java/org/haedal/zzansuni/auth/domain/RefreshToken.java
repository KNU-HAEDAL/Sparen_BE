package org.haedal.zzansuni.auth.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.haedal.zzansuni.user.domain.User;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class RefreshToken {
    @Id @Column(columnDefinition = "CHAR(36)")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition ="TIMESTAMP(0)", nullable = false)
    private LocalDateTime expiredAt;

    public static RefreshToken create(String id,User user,LocalDateTime refreshTokenExpireAt) {
        return RefreshToken.builder()
            .id(id)
            .user(user)
            .expiredAt(refreshTokenExpireAt)
            .build();
    }

    public Long getUserId() {
        return user.getId();
    }
}
