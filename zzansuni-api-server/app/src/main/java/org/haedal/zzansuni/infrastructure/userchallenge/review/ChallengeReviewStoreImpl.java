package org.haedal.zzansuni.infrastructure.userchallenge.review;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.userchallenge.review.ChallengeReview;
import org.haedal.zzansuni.domain.userchallenge.review.port.ChallengeReviewStore;
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
