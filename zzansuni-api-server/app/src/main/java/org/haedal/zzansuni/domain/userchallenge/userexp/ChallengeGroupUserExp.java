package org.haedal.zzansuni.domain.userchallenge.userexp;

import jakarta.persistence.*;
import lombok.*;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroup;
import org.haedal.zzansuni.domain.user.User;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "challenge_group_user_exp_unique",
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
