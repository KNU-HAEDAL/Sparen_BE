package org.haedal.zzansuni.infrastructure.challengegroup.challengereview;

import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.challengegroup.challengereview.ChallengeReview;
import org.haedal.zzansuni.domain.challengegroup.challengereview.ChallengeReviewReader;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChallengeReviewReaderImpl implements ChallengeReviewReader {

    private final ChallengeReviewRepository challengeReviewRepository;

    @Override
    public ChallengeReview getByChallengeId(Long challengeId) {
        return challengeReviewRepository.findByUserChallengeId(challengeId)
            .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Optional<ChallengeReview> findByUserChallengeId(Long challengeId) {
        return challengeReviewRepository.findByUserChallengeId(challengeId);
    }

}
