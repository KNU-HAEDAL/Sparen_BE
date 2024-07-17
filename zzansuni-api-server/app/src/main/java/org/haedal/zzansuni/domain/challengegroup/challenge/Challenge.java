package org.haedal.zzansuni.domain.challengegroup.challenge;

import jakarta.persistence.*;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.haedal.zzansuni.domain.BaseTimeEntity;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroup;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroupCommand;
import org.haedal.zzansuni.domain.challengegroup.DayType;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Challenge extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "challenge_group_id")
    private ChallengeGroup challengeGroup;

    @Column(nullable = false)
    private Integer requiredCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayType dayType;

    @Column(nullable = false)
    private Integer onceExp;

    @Column(nullable = false)
    private Integer successExp;

    @Column(nullable = false)
    private Integer difficulty;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;


    public static Challenge create(ChallengeGroupCommand.CreateChallenge command, ChallengeGroup group) {
        return Challenge.builder()
                .challengeGroup(group)
                .requiredCount(command.getRequiredCount())
                .dayType(command.getDayType())
                .onceExp(command.getOnceExp())
                .successExp(command.getSuccessExp())
                .difficulty(command.getDifficulty())
                .startDate(command.getStartDate())
                .endDate(command.getEndDate())
                .build();
    }
}
