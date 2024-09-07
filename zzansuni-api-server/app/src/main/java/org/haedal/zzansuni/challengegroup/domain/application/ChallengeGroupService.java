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

import java.util.ArrayList;
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

    /**
     * ChallengeGroupCommand.Update에서
     * id=-1인 경우는 새로운 Challenge이므로 create로 처리한다.
     * id가 존재하는 경우는 update로 처리한다.
     * id가 존재하지 않는 경우는 delete로 처리한다.
     */
    //TODO: 변경감지 없이 update
    @Transactional
    public void updateChallengeGroup(ChallengeGroupCommand.Update command) {
        ChallengeGroup challengeGroup = challengeGroupReader.getById(command.getId());

        List<Challenge> challenges = new ArrayList<>();
        Set<Challenge> existedChallenges = new HashSet<>();

        for (ChallengeGroupCommand.UpdateChallenge challenge : command.getUpdateChallenges()) {
            processChallenge(challenge, challengeGroup, challenges, existedChallenges);
        }

        challengeGroup.update(command);
        removeChallenges(challengeGroup, existedChallenges);
        challengeGroup.addChallenges(challenges);
    }

    private void processChallenge(ChallengeGroupCommand.UpdateChallenge updateChallengeCommand,
                                  ChallengeGroup challengeGroup,
                                  List<Challenge> newChallenges,
                                  Set<Challenge> existingChallenges)
    {
        if (updateChallengeCommand.getId().equals(-1L)) {
            newChallenges.add(Challenge.create(updateChallengeCommand.convertCreate(), challengeGroup));
        } else {
            Challenge updateChallenge = challengeReader.getById(updateChallengeCommand.getId());
            existingChallenges.add(updateChallenge);
            updateChallenge.update(updateChallengeCommand);
        }
    }

    private void removeChallenges(ChallengeGroup challengeGroup, Set<Challenge> existingChallenges) {
        List<Challenge> removeChallenges = challengeGroup.getChallenges().stream()
                .filter(challenge -> !existingChallenges.contains(challenge))
                .toList();
        challengeGroup.removeChallenges(removeChallenges);
    }
}
