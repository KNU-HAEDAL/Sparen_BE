package org.haedal.zzansuni.domain.challengegroup;

public interface ChallengeGroupReader {
    ChallengeGroup getById(Long challengeGroupId);

    ChallengeGroup getByIdWithChallenges(Long challengeGroupId);
}
