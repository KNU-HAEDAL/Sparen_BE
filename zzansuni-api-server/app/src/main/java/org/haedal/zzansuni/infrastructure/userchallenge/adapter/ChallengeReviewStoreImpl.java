package org.haedal.zzansuni.infrastructure.userchallenge.adapter;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.userchallenge.ChallengeReview;
import org.haedal.zzansuni.domain.userchallenge.port.ChallengeReviewStore;
import org.haedal.zzansuni.infrastructure.userchallenge.ChallengeReviewRepository;
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
