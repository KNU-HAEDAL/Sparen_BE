package org.haedal.zzansuni.infrastructure.challengegroup;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroupImage;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroupImageReader;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ChallengeGroupImageReaderImpl implements ChallengeGroupImageReader {
    private final ChallengeGroupImageRepository challengeGroupImageRepository;
    @Override
    public List<ChallengeGroupImage> getByChallengeGroupId(Long challengeGroupId) {
        return challengeGroupImageRepository.findByChallengeGroupId(challengeGroupId);
    }
}
