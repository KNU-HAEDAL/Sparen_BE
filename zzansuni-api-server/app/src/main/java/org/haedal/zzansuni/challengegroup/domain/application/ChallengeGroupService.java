package org.haedal.zzansuni.challengegroup.domain.application;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.challengegroup.domain.ChallengeGroup;
import org.haedal.zzansuni.challengegroup.domain.ChallengeGroupCommand;
import org.haedal.zzansuni.challengegroup.domain.port.ChallengeGroupReader;
import org.haedal.zzansuni.challengegroup.domain.port.ChallengeGroupStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChallengeGroupService {
    private final ChallengeGroupStore challengeGroupStore;
    private final ChallengeGroupReader challengeGroupReader;

    @Transactional
    public void createChallengeGroup(ChallengeGroupCommand.Create command) {
        ChallengeGroup challengeGroup = ChallengeGroup.create(command);
        challengeGroupStore.save(challengeGroup);
    }

    @Transactional
    public void deleteChallengeGroup(Long challengeGroupId) {
        ChallengeGroup challengeGroup = challengeGroupReader.getById(challengeGroupId);
        challengeGroupStore.delete(challengeGroup.getId());
    }
}
