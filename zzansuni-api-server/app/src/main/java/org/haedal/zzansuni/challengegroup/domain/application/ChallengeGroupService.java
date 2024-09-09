package org.haedal.zzansuni.challengegroup.domain.application;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.challengegroup.domain.Challenge;
import org.haedal.zzansuni.challengegroup.domain.ChallengeGroup;
import org.haedal.zzansuni.challengegroup.domain.ChallengeGroupCommand;
import org.haedal.zzansuni.challengegroup.domain.port.ChallengeGroupReader;
import org.haedal.zzansuni.challengegroup.domain.port.ChallengeGroupStore;
import org.haedal.zzansuni.challengegroup.domain.port.ChallengeReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChallengeGroupService {
    private final ChallengeGroupStore challengeGroupStore;
    private final ChallengeGroupReader challengeGroupReader;
    private final ChallengeReader challengeReader;

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


    @Transactional
    public void updateChallengeGroup(ChallengeGroupCommand.Update command) {
        ChallengeGroup challengeGroup = challengeGroupReader.getById(command.getId());
        challengeGroup.update(command);

        Set<Challenge> existingChallenges = new HashSet<>();
        createChallenges(challengeGroup, command.getCreateChallenges());
        updateChallenges(command.getUpdateChallenges(), existingChallenges);
        removeChallenges(challengeGroup, existingChallenges);
    }

    private void createChallenges(ChallengeGroup challengeGroup, List<ChallengeGroupCommand.CreateChallenge> createChallenges) {
        List<Challenge> newChallenges = createChallenges.stream()
                .map(challenge -> Challenge.create(challenge, challengeGroup))
                .toList();
        challengeGroup.addChallenges(newChallenges);
    }

    private void updateChallenges(List<ChallengeGroupCommand.UpdateChallenge> challenges, Set<Challenge> existingChallenges) {
        for (ChallengeGroupCommand.UpdateChallenge challenge : challenges) {
            Challenge updateChallenge = challengeReader.getById(challenge.getId());
            updateChallenge.update(challenge);
            existingChallenges.add(updateChallenge);
        }
    }

    private void removeChallenges(ChallengeGroup challengeGroup, Set<Challenge> existingChallenges) {
        List<Challenge> removeChallenges = challengeGroup.getChallenges().stream()
                .filter(challenge -> !existingChallenges.contains(challenge))
                .toList();
        challengeGroup.removeChallenges(removeChallenges);
    }
}
