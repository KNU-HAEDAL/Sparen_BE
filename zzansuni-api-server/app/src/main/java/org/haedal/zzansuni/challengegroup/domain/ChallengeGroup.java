package org.haedal.zzansuni.challengegroup.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.haedal.zzansuni.common.domain.BaseTimeEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(
    indexes = {
        @Index(
            name = "idx_challenge_group_join_start_date_join_end_date_category",
            columnList = "join_start_date, join_end_date, category"
        ),
    }
)
public class ChallengeGroup extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false, length = 1000)
    private String guide;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChallengeCategory category;

    @Column(nullable = false)
    private LocalDate joinStartDate;

    @Column(nullable = false)
    private LocalDate joinEndDate;


    @Column(nullable = false)
    private Integer cumulativeCount;

    @OneToMany(mappedBy = "challengeGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Challenge> challenges = new ArrayList<>();

    public static ChallengeGroup create(ChallengeGroupCommand.Create command) {
        List<Challenge> challenges = new ArrayList<>();
        ChallengeGroup group =  ChallengeGroup.builder()
                .category(command.getCategory())
                .title(command.getTitle())
                .content(command.getContent())
                .guide(command.getGuide())
                .cumulativeCount(0)
                .joinStartDate(command.getJoinStartDate())
                .joinEndDate(command.getJoinEndDate())
                .challenges(challenges)
                .build();
        command.getCreateChallenges().stream().map(challenge -> Challenge.create(challenge, group))
                .forEach(challenges::add);
        return group;
    }

    public ChallengeGroup update(ChallengeGroupCommand.Update command) {
        this.category = command.getCategory();
        this.title = command.getTitle();
        this.content = command.getContent();
        this.guide = command.getGuide();
        this.joinStartDate = command.getJoinStartDate();
        this.joinEndDate = command.getJoinEndDate();
        updateChallenges(command.getUpdateChallenges());
        command.getCreateChallenges().stream().map(challenge -> Challenge.create(challenge,this))
                .forEach(this.challenges::add);
        return this;
    }

    public void updateChallenges(List<ChallengeGroupCommand.UpdateChallenge> command) {
        List<Challenge> removeChallenges = new ArrayList<>();
        for (Challenge existingChallenge : this.challenges) {
            for (ChallengeGroupCommand.UpdateChallenge updateCommand : command) {
                if (existingChallenge.getId().equals(updateCommand.getId())) {
                    existingChallenge.update(updateCommand);
                } else {
                    removeChallenges.add(existingChallenge);
                }
            }
        }
        this.challenges.removeAll(removeChallenges);
    }


}
