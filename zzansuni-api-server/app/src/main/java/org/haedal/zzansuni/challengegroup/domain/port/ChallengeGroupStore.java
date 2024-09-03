package org.haedal.zzansuni.challengegroup.domain.port;

import org.haedal.zzansuni.challengegroup.domain.ChallengeGroup;

public interface ChallengeGroupStore {
    ChallengeGroup save(ChallengeGroup challengeGroup);
    void delete(Long challengeGroupId);
}
