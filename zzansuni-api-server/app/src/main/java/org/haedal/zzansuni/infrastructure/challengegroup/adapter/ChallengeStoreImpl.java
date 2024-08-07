package org.haedal.zzansuni.infrastructure.challengegroup.adapter;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.challengegroup.port.ChallengeStore;
import org.haedal.zzansuni.infrastructure.challengegroup.ChallengeRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChallengeStoreImpl implements ChallengeStore {

    private final ChallengeRepository challengeRepository;

}
