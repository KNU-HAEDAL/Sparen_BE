package org.haedal.zzansuni.challengegroup.domain.port;

import org.haedal.zzansuni.challengegroup.domain.ChallengeGroupImage;

import java.util.List;

public interface ChallengeGroupImageStore {
    void saveAll(List<ChallengeGroupImage> challengeGroupImages);
    void deleteAllByChallengeGroupId(Long challengeGroupId);
}
