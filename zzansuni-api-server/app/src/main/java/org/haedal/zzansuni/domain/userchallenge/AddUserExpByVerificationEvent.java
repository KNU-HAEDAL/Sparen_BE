package org.haedal.zzansuni.domain.userchallenge;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class AddUserExpByVerificationEvent {
    private Long userId;
    private Integer acquiredExp;
    private Long challengeGroupId;
}
