package org.haedal.zzansuni.infrastructure.challengegroup;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroup;
import org.haedal.zzansuni.domain.challengegroup.port.ChallengeGroupStore;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChallengeGroupStoreImpl implements ChallengeGroupStore {
    private final ChallengeGroupRepository challengeGroupRepository;
    @Override
    public ChallengeGroup save(ChallengeGroup challengeGroup) {
        return challengeGroupRepository.save(challengeGroup);
    }

    @Override
    public void delete(Long challengeGroupId) {
        challengeGroupRepository.deleteById(challengeGroupId);
    }
}
