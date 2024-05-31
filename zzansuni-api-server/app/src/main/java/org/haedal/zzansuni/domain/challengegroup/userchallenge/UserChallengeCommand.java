package org.haedal.zzansuni.domain.challengegroup.userchallenge;

import lombok.Builder;
import lombok.Getter;
import org.haedal.zzansuni.core.utils.SelfValidating;

public class UserChallengeCommand {

    @Getter
    @Builder
    public static class Participate extends SelfValidating<Participate> {

        private Long challengeId;
        private Long userId;

        public Participate(Long challengeId, Long userId) {
            this.challengeId = challengeId;
            this.userId = userId;
            this.validateSelf();
        }
    }

}
