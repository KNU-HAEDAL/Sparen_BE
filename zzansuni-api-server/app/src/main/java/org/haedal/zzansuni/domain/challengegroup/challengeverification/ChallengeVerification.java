package org.haedal.zzansuni.domain.challengegroup.challengeverification;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.haedal.zzansuni.domain.BaseTimeEntity;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeCommand.Verificate;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeVerificationStatus;
import org.haedal.zzansuni.domain.challengegroup.userchallenge.UserChallenge;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class ChallengeVerification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_challenge_id")
    private UserChallenge userChallenge;

    private String imageUrl;

    private String content;

    @Enumerated(EnumType.STRING)
    private ChallengeVerificationStatus status;

    public static ChallengeVerification create(Verificate command,
        UserChallenge userChallenge) {
        return ChallengeVerification.builder()
            .userChallenge(userChallenge)
            .imageUrl(null)
            .content(command.getContent())
            .status(ChallengeVerificationStatus.APPROVED)
            .build();
    }

}
