package org.haedal.zzansuni.userchallenge.domain.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class SubUserExpByVerificationEvent {
    private Long userId;
    private Integer subExp;
    private Long challengeGroupId;
}
