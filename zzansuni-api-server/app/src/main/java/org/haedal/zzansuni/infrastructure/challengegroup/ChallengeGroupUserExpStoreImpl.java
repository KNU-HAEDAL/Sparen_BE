package org.haedal.zzansuni.infrastructure.challengegroup;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroupUserExp;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroupUserExpStore;
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
