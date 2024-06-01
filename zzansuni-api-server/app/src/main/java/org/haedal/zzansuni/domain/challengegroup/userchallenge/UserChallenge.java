package org.haedal.zzansuni.domain.challengegroup.userchallenge;

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
import org.haedal.zzansuni.domain.challengegroup.challenge.Challenge;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeStatus;
import org.haedal.zzansuni.domain.user.User;

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

    public static UserChallenge create(Challenge challenge, User user) {
        return UserChallenge.builder()
            .challenge(challenge)
            .user(user)
            .status(ChallengeStatus.PROCEEDING)
            .build();
    }

    private void complete() {
        this.status = ChallengeStatus.SUCCESS;
    }

    /**
     * 챌린지 인증 참여횟수와 필요참여획수가 같으면 챌린지 완료로 변경
     */
    public void tryComplete(Integer currentCount, Integer requiredCount) {
        if (currentCount.equals(requiredCount)) {
            complete();
        }
    }


}
