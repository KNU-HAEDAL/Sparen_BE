package org.haedal.zzansuni.domain.challengegroup;

import java.util.List;

public interface ChallengeGroupImageReader {
    List<ChallengeGroupImage> getByChallengeGroupId(Long challengeGroupId);
}
