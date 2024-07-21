package org.haedal.zzansuni.domain.challengegroup.port;

import org.haedal.zzansuni.domain.challengegroup.ChallengeGroupImage;

import java.util.List;

public interface ChallengeGroupImageReader {
    List<ChallengeGroupImage> getByChallengeGroupId(Long challengeGroupId);
}
