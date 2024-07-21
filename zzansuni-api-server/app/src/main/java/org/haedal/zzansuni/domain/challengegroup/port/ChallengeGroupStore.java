package org.haedal.zzansuni.domain.challengegroup.port;

import org.haedal.zzansuni.domain.challengegroup.ChallengeGroup;

public interface ChallengeGroupStore {
    ChallengeGroup save(ChallengeGroup challengeGroup);
    void delete(Long challengeGroupId);
}
