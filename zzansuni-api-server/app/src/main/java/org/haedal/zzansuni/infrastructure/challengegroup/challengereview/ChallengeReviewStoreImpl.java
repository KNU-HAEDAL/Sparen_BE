package org.haedal.zzansuni.infrastructure.challengegroup.challengereview;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.challengegroup.challengereview.ChallengeReview;
import org.haedal.zzansuni.domain.challengegroup.challengereview.ChallengeReviewStore;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@RequiredArgsConstructor
public class ChallengeReviewStoreImpl implements ChallengeReviewStore {

    private final ChallengeReviewRepository challengeReviewRepository;

    @Override
    public ChallengeReview store(ChallengeReview challengeReview) {
        return challengeReviewRepository.save(challengeReview);
    }
}
