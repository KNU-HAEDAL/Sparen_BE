package org.haedal.zzansuni.infrastructure.challengegroup.challenge;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeReader;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChallengeReaderImpl implements ChallengeReader {

    private final ChallengeRepository challengeRepository;


}
