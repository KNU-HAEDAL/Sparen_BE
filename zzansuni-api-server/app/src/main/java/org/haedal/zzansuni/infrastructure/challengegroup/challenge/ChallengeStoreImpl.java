package org.haedal.zzansuni.infrastructure.challengegroup.challenge;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.challengegroup.challenge.port.ChallengeStore;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChallengeStoreImpl implements ChallengeStore {

    private final ChallengeRepository challengeRepository;

}
