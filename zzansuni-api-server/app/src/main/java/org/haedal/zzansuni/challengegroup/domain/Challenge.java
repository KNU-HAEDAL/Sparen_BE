package org.haedal.zzansuni.challengegroup.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.haedal.zzansuni.common.domain.BaseTimeEntity;

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
    private Integer onceExp;

    @Column(nullable = false)
    private Integer successExp;

    @Column(nullable = false)
    private Integer difficulty;

    @Column(nullable = false)
    private Integer requiredCount;

    @Column(nullable = false)
    private Integer activePeriod;


    public static Challenge create(ChallengeGroupCommand.CreateChallenge command, ChallengeGroup group) {
        return Challenge.builder()
                .challengeGroup(group)
                .requiredCount(command.getRequiredCount())
                .onceExp(command.getOnceExp())
                .successExp(command.getSuccessExp())
                .difficulty(command.getDifficulty())
                .activePeriod(command.getActivePeriod())
                .build();
    }

    /**
     * 챌린지 그룹 아이디 반환
     * FK는 LAZY 여도 이미 영속성 컨텍스트에 존재하므로 추가 조회 없이 바로 접근 가능
     */
    public Long getChallengeGroupId() {
        return challengeGroup.getId();
    }
}
