package org.haedal.zzansuni.userchallenge.domain;

import jakarta.persistence.*;
import lombok.*;
import org.haedal.zzansuni.challengegroup.domain.ChallengeGroup;
import org.haedal.zzansuni.user.domain.User;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_challenge_group_user_exp_challenge_group_user",
            columnNames = {"challenge_group_id", "user_id"}
        )
    }
)
public class ChallengeGroupUserExp {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "challenge_group_id", nullable = false)
    private ChallengeGroup challengeGroup;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer totalExp;

    public static ChallengeGroupUserExp create(ChallengeGroup challengeGroup, User user) {
        return ChallengeGroupUserExp.builder()
                .challengeGroup(challengeGroup)
                .user(user)
                .totalExp(0)
                .build();
    }

    public void addExp(Integer exp) {
        this.totalExp += exp;
    }
}
