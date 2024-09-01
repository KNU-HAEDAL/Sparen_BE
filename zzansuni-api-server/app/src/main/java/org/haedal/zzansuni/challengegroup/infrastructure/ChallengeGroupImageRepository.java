package org.haedal.zzansuni.challengegroup.infrastructure;

import org.haedal.zzansuni.challengegroup.domain.ChallengeGroupImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeGroupImageRepository extends JpaRepository<ChallengeGroupImage, Long> {
    List<ChallengeGroupImage> findByChallengeGroupId(Long challengeGroupId);
}
