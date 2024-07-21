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
import org.haedal.zzansuni.domain.challengegroup.verification.ChallengeVerification;
import org.haedal.zzansuni.domain.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            name = "user_challenge_unique",
            columnNames = {"challenge_id", "user_id"}
        )
    }
)
public class UserChallenge extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "challenge_id", nullable = false)
    private Challenge challenge;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
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

    /**
     * 챌린지 인증 추가
     * 1. 챌린지 인증을 추가
     * 2. 챌린지 인증을 추가하면서 챌린지 인증에 따른 경험치를 추가
     * 3. 챌린지를 완료한 경우 챌린지 완료로 변경, 경험치 추가
     * 4. 챌린지그룹-경험치 추가 이벤트를 반환
     */
    public AddExpToChallengeGroupEvent addChallengeVerification(ChallengeCommand.VerificationCreate command) {
        ChallengeVerification challengeVerification = ChallengeVerification.create(command, this);
        this.challengeVerifications.add(challengeVerification);

        int acquiredExp = this.challenge.getOnceExp();
        user.addExp(challenge.getOnceExp());

        // 만약 챌린지 인증 참여횟수와 필요참여획수가 같으면 챌린지 완료로 변경
        if (this.challengeVerifications.size() == this.challenge.getRequiredCount()) {
            user.addExp(challenge.getSuccessExp());
            acquiredExp += challenge.getSuccessExp();
            this.completeChallengeStatus();
        }
        return AddExpToChallengeGroupEvent
                .of(user.getId(), acquiredExp, challenge.getChallengeGroupId());
    }


    private void completeChallengeStatus() {
        this.status = ChallengeStatus.SUCCESS;
    }

    /**
     * 챌린지 성공일자 가져오기
     * [챌린지 인증]을 통해 성공한 가장 최근 날짜를 가져온다.
     */
    public Optional<LocalDate> getSuccessDate() {
        return this.challengeVerifications.stream()
            .map(ChallengeVerification::getCreatedAt)
            .max(LocalDateTime::compareTo)
            .map(LocalDateTime::toLocalDate);
    }


}
