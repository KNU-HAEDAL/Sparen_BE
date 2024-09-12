package org.haedal.zzansuni.userchallenge.domain;

import jakarta.persistence.*;
import lombok.*;
import org.haedal.zzansuni.common.domain.BaseTimeEntity;
import org.haedal.zzansuni.challengegroup.domain.ChallengeCommand;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class ChallengeVerification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_challenge_id", nullable = false)
    private UserChallenge userChallenge;

    private String imageUrl;

    @Column(nullable = false, length = 1000)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChallengeVerificationStatus status;

    public static ChallengeVerification create(ChallengeCommand.VerificationCreate command, UserChallenge userChallenge) {
        return ChallengeVerification.builder()
            .userChallenge(userChallenge)
            .imageUrl(command.getImageUrl())
            .content(command.getContent())
            .status(ChallengeVerificationStatus.PRE_APPROVED)
            .build();
    }

    public void approve() {
        this.status = ChallengeVerificationStatus.APPROVED;
    }

    public void reject() {
        this.status = ChallengeVerificationStatus.REJECTED;
    }

}
