package org.haedal.zzansuni.challengegroup.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.haedal.zzansuni.common.domain.BaseTimeEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
public class ChallengeGroupImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "challenge_group_id", nullable = false)
    private ChallengeGroup challengeGroup;

    public static ChallengeGroupImage create(ChallengeGroup challengeGroup, String imageUrl) {
        return ChallengeGroupImage.builder()
                .challengeGroup(challengeGroup)
                .imageUrl(imageUrl)
                .build();
    }
}
