package org.haedal.zzansuni.infrastructure.challengegroup;

import org.haedal.zzansuni.domain.challengegroup.ChallengeGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChallengeGroupRepository extends JpaRepository<ChallengeGroup, Long> {
    @Query("select cg from ChallengeGroup cg join fetch cg.challenges where cg.id = :challengeGroupId")
    Optional<ChallengeGroup> findByIdWithChallenges(Long challengeGroupId);
}
