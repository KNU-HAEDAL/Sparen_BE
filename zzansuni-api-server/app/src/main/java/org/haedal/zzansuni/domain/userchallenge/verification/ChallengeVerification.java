package org.haedal.zzansuni.domain.userchallenge.verification;

import jakarta.persistence.*;
import lombok.*;
import org.haedal.zzansuni.domain.BaseTimeEntity;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeCommand;
import org.haedal.zzansuni.domain.userchallenge.UserChallenge;

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

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChallengeVerificationStatus status;

    public static ChallengeVerification create(ChallengeCommand.VerificationCreate command, UserChallenge userChallenge) {
        return ChallengeVerification.builder()
            .userChallenge(userChallenge)
            .imageUrl(command.getImageUrl())
            .content(command.getContent())
            .status(ChallengeVerificationStatus.APPROVED)
            .build();
    }

}
