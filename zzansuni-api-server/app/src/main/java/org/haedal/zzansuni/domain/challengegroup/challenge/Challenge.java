package org.haedal.zzansuni.domain.challengegroup.challenge;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_group_id")
    private ChallengeGroup challengeGroup;

    private Integer requiredCount;

    @Enumerated(EnumType.STRING)
    private DayType dayType;

    private Integer onceExp;

    private Integer successExp;

    private Integer difficulty;

    private LocalDate startDate;

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
