package org.haedal.zzansuni.challengegroup.infrastructure;

import org.haedal.zzansuni.challengegroup.domain.ChallengeGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChallengeGroupRepository extends JpaRepository<ChallengeGroup, Long> {

    @Query("select cg from ChallengeGroup cg left join fetch cg.challenges where cg.id = :challengeGroupId")
    Optional<ChallengeGroup> findByIdWithChallenges(Long challengeGroupId);
}
