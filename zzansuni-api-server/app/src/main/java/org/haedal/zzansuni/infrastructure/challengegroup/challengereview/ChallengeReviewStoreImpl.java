package org.haedal.zzansuni.infrastructure.challengegroup.challengereview;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.challengegroup.review.ChallengeReview;
import org.haedal.zzansuni.domain.challengegroup.review.ChallengeReviewStore;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChallengeReviewStoreImpl implements ChallengeReviewStore {

    private final ChallengeReviewRepository challengeReviewRepository;

    @Override
    public ChallengeReview store(ChallengeReview challengeReview) {
        return challengeReviewRepository.save(challengeReview);
    }
}
