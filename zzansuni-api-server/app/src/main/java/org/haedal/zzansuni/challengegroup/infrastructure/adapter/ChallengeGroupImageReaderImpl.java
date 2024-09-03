package org.haedal.zzansuni.challengegroup.infrastructure.adapter;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.challengegroup.domain.ChallengeGroupImage;
import org.haedal.zzansuni.challengegroup.domain.port.ChallengeGroupImageReader;
import org.haedal.zzansuni.challengegroup.infrastructure.ChallengeGroupImageRepository;
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
