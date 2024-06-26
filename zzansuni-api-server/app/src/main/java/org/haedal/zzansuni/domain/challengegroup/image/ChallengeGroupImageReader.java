package org.haedal.zzansuni.domain.challengegroup.image;

import java.util.List;

public interface ChallengeGroupImageReader {
    List<ChallengeGroupImage> getByChallengeGroupId(Long challengeGroupId);
}
