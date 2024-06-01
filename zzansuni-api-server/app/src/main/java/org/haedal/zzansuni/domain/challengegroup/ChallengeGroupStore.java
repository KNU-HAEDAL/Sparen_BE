package org.haedal.zzansuni.domain.challengegroup;

public interface ChallengeGroupStore {
    ChallengeGroup save(ChallengeGroup challengeGroup);
    void delete(Long challengeGroupId);
}
