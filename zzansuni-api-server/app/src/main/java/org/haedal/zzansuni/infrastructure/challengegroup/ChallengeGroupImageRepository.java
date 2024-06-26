package org.haedal.zzansuni.infrastructure.challengegroup;

import org.haedal.zzansuni.domain.challengegroup.image.ChallengeGroupImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeGroupImageRepository extends JpaRepository<ChallengeGroupImage, Long> {
    List<ChallengeGroupImage> findByChallengeGroupId(Long challengeGroupId);
}
