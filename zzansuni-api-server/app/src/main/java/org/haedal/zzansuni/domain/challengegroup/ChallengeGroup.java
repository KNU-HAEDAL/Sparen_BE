package org.haedal.zzansuni.domain.challengegroup;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.haedal.zzansuni.domain.BaseTimeEntity;
import org.haedal.zzansuni.domain.challengegroup.challenge.Challenge;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class ChallengeGroup extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ChallengeCategory category;

    private String title;

    private String content;

    private String guide;

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
                .challenges(challenges)
                .build();
        command.getCreateChallenges().stream().map(challenge -> Challenge.create(challenge, group))
                .forEach(challenges::add);
        return group;
    }
}
