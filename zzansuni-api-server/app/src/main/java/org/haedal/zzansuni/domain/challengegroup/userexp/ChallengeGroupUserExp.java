package org.haedal.zzansuni.domain.challengegroup.userexp;

import jakarta.persistence.*;
import lombok.*;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroup;
import org.haedal.zzansuni.domain.user.User;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChallengeGroupUserExp {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_group_id")
    private ChallengeGroup challengeGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

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
