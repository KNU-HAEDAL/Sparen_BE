package org.haedal.zzansuni.challengegroup.infrastructure.adapter;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.challengegroup.domain.ChallengeGroup;
import org.haedal.zzansuni.challengegroup.domain.port.ChallengeGroupStore;
import org.haedal.zzansuni.challengegroup.infrastructure.ChallengeGroupRepository;
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
