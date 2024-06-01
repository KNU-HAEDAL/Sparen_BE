package org.haedal.zzansuni.infrastructure.challengegroup.challengereview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.challengegroup.review.ChallengeReview;
import org.haedal.zzansuni.domain.challengegroup.review.ChallengeReviewReader;
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

    @Override
    public Map<Long, Boolean> getReviewWrittenMapByUserChallengeId(List<Long> userChallengeIds) {
        List<ChallengeReview> byUserChallengeIdIn = challengeReviewRepository.findByUserChallengeIdIn(
            userChallengeIds);
        // 리뷰가 있으면 map에 true, 없으면 false로 Map 생성

        //1. userChallengeIds를 순회하면서 reviewWrittenMap에 false로 초기화
        Map<Long, Boolean> reviewWrittenMap =
            userChallengeIds.stream()
                .collect(HashMap::new, (m, v) -> m.put(v, false), HashMap::putAll);

        //2. byUserChallengeIdIn을 순회하면서
        // 리뷰가 존재했다면, reviewWrittenMap에 true로 변경
        byUserChallengeIdIn.forEach(
            review -> reviewWrittenMap.put(review.getUserChallenge().getId(), true));

        return reviewWrittenMap;
    }

}
