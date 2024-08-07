package org.haedal.zzansuni.infrastructure.challengegroup.adapter;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroupUserExp;
import org.haedal.zzansuni.domain.challengegroup.port.ChallengeGroupUserExpStore;
import org.haedal.zzansuni.infrastructure.challengegroup.ChallengeGroupUserExpRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChallengeGroupUserExpStoreImpl implements ChallengeGroupUserExpStore {
    private final ChallengeGroupUserExpRepository challengeGroupUserExpRepository;

    @Override
    public ChallengeGroupUserExp store(ChallengeGroupUserExp challengeGroupUserExp) {
        return challengeGroupUserExpRepository.save(challengeGroupUserExp);
    }
}
