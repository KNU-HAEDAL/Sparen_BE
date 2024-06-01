package org.haedal.zzansuni.domain.challengegroup.review;

import jakarta.persistence.Entity;
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
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeCommand;
import org.haedal.zzansuni.domain.challengegroup.userchallenge.UserChallenge;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class ChallengeReview extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_challenge_id")
    private UserChallenge userChallenge;

    private String content;

    private Integer rating;

    // 쿼리 성능을 위해 비정규화
    private Long challengeGroupId;

    public static ChallengeReview create(UserChallenge userChallenge,
        ChallengeCommand.ReviewCreate command, Long challengeGroupId) {
        return ChallengeReview.builder()
            .userChallenge(userChallenge)
            .content(command.getContent())
            .rating(command.getRating())
            .challengeGroupId(challengeGroupId)
            .build();
    }

}
