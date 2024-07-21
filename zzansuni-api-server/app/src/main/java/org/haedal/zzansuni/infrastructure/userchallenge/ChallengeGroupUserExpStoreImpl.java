package org.haedal.zzansuni.infrastructure.userchallenge;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.userchallenge.userexp.ChallengeGroupUserExp;
import org.haedal.zzansuni.domain.userchallenge.userexp.port.ChallengeGroupUserExpStore;
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
