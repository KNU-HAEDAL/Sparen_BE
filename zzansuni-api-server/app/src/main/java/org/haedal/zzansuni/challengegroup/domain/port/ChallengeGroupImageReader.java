package org.haedal.zzansuni.challengegroup.domain.port;

import org.haedal.zzansuni.challengegroup.domain.ChallengeGroupImage;

import java.util.List;

public interface ChallengeGroupImageReader {
    List<ChallengeGroupImage> getByChallengeGroupId(Long challengeGroupId);
}
