package org.haedal.zzansuni.infrastructure.challengegroup;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroup;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroupReader;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class ChallengeGroupReaderImpl implements ChallengeGroupReader {
    private final ChallengeGroupRepository challengeGroupRepository;
    @Override
    public ChallengeGroup getById(Long challengeGroupId) {
        return challengeGroupRepository
                .findById(challengeGroupId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public ChallengeGroup getByIdWithChallenges(Long challengeGroupId) {
        return challengeGroupRepository
                .findByIdWithChallenges(challengeGroupId)
                .orElseThrow(NoSuchElementException::new);
    }
}
