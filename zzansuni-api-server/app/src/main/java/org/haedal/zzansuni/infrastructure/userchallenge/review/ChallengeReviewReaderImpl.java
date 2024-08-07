package org.haedal.zzansuni.infrastructure.userchallenge.review;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.userchallenge.ChallengeReview;
import org.haedal.zzansuni.domain.userchallenge.port.ChallengeReviewReader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.*;

import static org.haedal.zzansuni.domain.userchallenge.QChallengeReview.challengeReview;


@Component
@RequiredArgsConstructor
public class ChallengeReviewReaderImpl implements ChallengeReviewReader {

    private final JPAQueryFactory queryFactory;
    private final ChallengeReviewRepository challengeReviewRepository;

    @Override
    public ChallengeReview getByUserChallengeId(Long challengeId) {
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

    /**
     * 챌린지 그룹 N+1 문제 해결 못함
     */
    @Override
    public Page<ChallengeReview> getChallengeReviewPageByChallengeGroupId(Long challengeGroupId,
        Pageable pageable) {
        Long count = queryFactory
            .select(challengeReview.count())
            .from(challengeReview)
            .where(challengeReview.challengeGroupId.eq(challengeGroupId))
            .fetchOne();

        List<ChallengeReview> challengeReviews = queryFactory
            .select(challengeReview)
            .from(challengeReview)
            .where(challengeReview.challengeGroupId.eq(challengeGroupId))
            .leftJoin(challengeReview.userChallenge)
            .fetchJoin() // userChallenge 엔티티 fetch join
            .leftJoin(challengeReview.userChallenge.user)
            .fetchJoin() // user 엔티티 fetch join
            .leftJoin(challengeReview.userChallenge.challenge)
            .fetchJoin() // challenge 엔티티 fetch join
            .orderBy(challengeReview.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(challengeReviews, pageable, count == null ? 0 : count);

    }

    @Override
    public Page<ChallengeReview> getChallengeReviewPage(Pageable pageable) {
        Long count = queryFactory
            .select(challengeReview.count())
            .from(challengeReview)
            .fetchOne();

        List<ChallengeReview> challengeReviews = queryFactory
            .select(challengeReview)
            .from(challengeReview)
            .leftJoin(challengeReview.userChallenge)
            .fetchJoin() // userChallenge 엔티티 fetch join
            .leftJoin(challengeReview.userChallenge.user)
            .fetchJoin() // user 엔티티 fetch join
            .leftJoin(challengeReview.userChallenge.challenge)
            .fetchJoin() // challenge 엔티티 fetch join
            .orderBy(challengeReview.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(challengeReviews, pageable, count == null ? 0 : count);
    }

    @Override
    public List<ChallengeReview> findByChallengeGroupId(Long challengeGroupId) {
        return queryFactory
            .select(challengeReview)
            .from(challengeReview)
            .where(challengeReview.challengeGroupId.eq(challengeGroupId))
            .fetch();
    }
}
