package org.haedal.zzansuni.challengegroup.infrastructure.adapter;

import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.challengegroup.domain.Challenge;
import org.haedal.zzansuni.challengegroup.domain.port.ChallengeReader;
import org.haedal.zzansuni.challengegroup.infrastructure.ChallengeRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChallengeReaderImpl implements ChallengeReader {

    private final ChallengeRepository challengeRepository;

    @Override
    public Challenge getById(Long id) {
        return findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Optional<Challenge> findById(Long id) {
        return challengeRepository.findById(id);
    }


}
