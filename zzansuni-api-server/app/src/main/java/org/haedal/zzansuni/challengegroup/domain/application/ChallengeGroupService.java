package org.haedal.zzansuni.challengegroup.domain.application;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.challengegroup.domain.ChallengeGroup;
import org.haedal.zzansuni.challengegroup.domain.ChallengeGroupCommand;
import org.haedal.zzansuni.challengegroup.domain.ChallengeGroupImage;
import org.haedal.zzansuni.challengegroup.domain.port.ChallengeGroupImageStore;
import org.haedal.zzansuni.challengegroup.domain.port.ChallengeGroupReader;
import org.haedal.zzansuni.challengegroup.domain.port.ChallengeGroupStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ChallengeGroupService {
    private final ChallengeGroupStore challengeGroupStore;
    private final ChallengeGroupReader challengeGroupReader;
    private final ChallengeGroupImageStore challengeGroupImageStore;

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
    }

    /**
     * 1. 기존 이미지 삭제
     * 2. 새로운 이미지 저장
     */
    @Transactional
    public void updateChallengeGroupImages(Long challengeGroupId, List<String> imageUrls) {
        ChallengeGroup challengeGroup = challengeGroupReader.getById(challengeGroupId);

        challengeGroupImageStore.deleteAllByChallengeGroupId(challengeGroupId);
        List<ChallengeGroupImage> challengeGroupImages = imageUrls.stream()
                .map(imageUrl -> ChallengeGroupImage.create(challengeGroup, imageUrl))
                .toList();
        challengeGroupImageStore.saveAll(challengeGroupImages);
    }
}
