package org.haedal.zzansuni.challengereview.infrastructure;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.challengereview.domain.ChallengeReviewStore;
import org.haedal.zzansuni.challengereview.domain.ChallengeReview;
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
