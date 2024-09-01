package org.haedal.zzansuni.userchallenge.infrastructure.adapter;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.userchallenge.domain.ChallengeReview;
import org.haedal.zzansuni.userchallenge.domain.port.ChallengeReviewStore;
import org.haedal.zzansuni.userchallenge.infrastructure.ChallengeReviewRepository;
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
