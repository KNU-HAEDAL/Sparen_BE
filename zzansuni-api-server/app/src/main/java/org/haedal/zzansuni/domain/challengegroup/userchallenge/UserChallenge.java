package org.haedal.zzansuni.domain.challengegroup.userchallenge;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.haedal.zzansuni.domain.BaseTimeEntity;
import org.haedal.zzansuni.domain.challengegroup.challenge.Challenge;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeCommand;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeStatus;
import org.haedal.zzansuni.domain.challengegroup.challengeverification.ChallengeVerification;
import org.haedal.zzansuni.domain.user.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class UserChallenge extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private ChallengeStatus status;

    @OneToMany(mappedBy = "userChallenge", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChallengeVerification> challengeVerifications = new ArrayList<>();

    public static UserChallenge create(Challenge challenge, User user) {
        return UserChallenge.builder()
                .challenge(challenge)
                .user(user)
                .status(ChallengeStatus.PROCEEDING)
                .build();
    }

    public void addChallengeVerification(ChallengeCommand.VerificationCreate command) {
        ChallengeVerification challengeVerification = ChallengeVerification.create(command, this);
        this.challengeVerifications.add(challengeVerification);

        // 만약 챌린지 인증 참여횟수와 필요참여획수가 같으면 챌린지 완료로 변경
        if(this.challengeVerifications.size() == this.challenge.getRequiredCount()) {
            complete();
        }
    }


    private void complete() {
        this.status = ChallengeStatus.SUCCESS;
    }


}
