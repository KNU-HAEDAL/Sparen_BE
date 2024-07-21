package org.haedal.zzansuni.domain.challengegroup.userchallenge;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class AddExpToChallengeGroupEvent {
    private Long userId;
    private Integer acquiredExp;
    private Long challengeGroupId;
}
