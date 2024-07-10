package org.haedal.zzansuni.infrastructure.challengegroup.userchallenge;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.haedal.zzansuni.domain.challengegroup.challenge.ChallengeStatus;
import org.haedal.zzansuni.domain.challengegroup.userchallenge.QUserChallenge;
import org.haedal.zzansuni.domain.challengegroup.userchallenge.UserChallenge;
import org.haedal.zzansuni.domain.challengegroup.userchallenge.UserChallengeReader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserChallengeReaderImpl implements UserChallengeReader {

    private final JPAQueryFactory queryFactory;
    private final UserChallengeRepository userChallengeRepository;

    @Override
    public UserChallenge getById(Long id) {
        return findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public UserChallenge getByIdWithVerificationAndChallenge(Long id) {
        return userChallengeRepository
            .findByIdWithFetchLazy(id)
            .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public UserChallenge getByChallengeIdWithVerificationAndChallenge(Long challengeId, Long userId) {
        return userChallengeRepository
            .findByChallengeIdWithFetchLazy(challengeId, userId)
            .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Optional<UserChallenge> findById(Long id) {
        return userChallengeRepository.findById(id);
    }

    @Override
    public UserChallenge getByUserIdAndChallengeId(Long userId, Long challengeId) {
        return findByUserIdAndChallengeId(userId, challengeId)
            .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Optional<UserChallenge> findByUserIdAndChallengeId(Long userId, Long challengeId) {
        return userChallengeRepository.findByUserIdAndChallengeId(userId, challengeId);
    }

    /**
     * userId로 현재 진행중인 챌린지 조회 <p> 페이징 처리를 하면서, userChallenge에서 이와 연관되어 있는 challenge, challengeGroup,
     * challengeVerifications를 가져온다.
     * <p>
     * 해당 방법을 통해 연관된 엔터티를 모두 가져와 N+1 문제를 해결한다.
     */
    @Override
    public Page<UserChallenge> getCurrentChallengePageByUserId(Long userId, Pageable pageable) {
        Long count = queryFactory
            .select(QUserChallenge.userChallenge.count())
            .from(QUserChallenge.userChallenge)
            .where(eqUserId(userId), eqProceeding())
            .fetchOne();
        List<UserChallenge> userChallenges = queryFactory
            .selectFrom(QUserChallenge.userChallenge)
            .where(eqUserId(userId), eqProceeding())
            .join(QUserChallenge.userChallenge.challenge).
            fetchJoin() // manyToOne 관계
            .join(QUserChallenge.userChallenge.challenge.challengeGroup)
            .fetchJoin() // manyToOne의 manyToOne 관계 엔티티를 가져옴
            .leftJoin(QUserChallenge.userChallenge.challengeVerifications)
            .fetchJoin() // oneToMany 관계
            .orderBy(QUserChallenge.userChallenge.id.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(userChallenges, pageable, count == null ? 0 : count);
    }

    /**
     * userId로 현재 완료중인 챌린지 조회 <p> 페이징 처리를 하면서, userChallenge에서 이와 연관되어 있는 challenge, challengeGroup,
     * challengeVerifications를 가져온다.
     * <p>
     * 해당 방법을 통해 연관된 엔터티를 모두 가져와 N+1 문제를 해결한다.
     */
    @Override
    public Page<UserChallenge> getCompletedChallengePageByUserId(Long userId, Pageable pageable) {
        Long count = queryFactory
            .select(QUserChallenge.userChallenge.count())
            .from(QUserChallenge.userChallenge)
            .where(eqUserId(userId), eqProceeding())
            .fetchOne();
        List<UserChallenge> userChallenges = queryFactory
            .selectFrom(QUserChallenge.userChallenge)
            .where(eqUserId(userId), neProceeding())
            .join(QUserChallenge.userChallenge.challenge).
            fetchJoin() // manyToOne 관계
            .join(QUserChallenge.userChallenge.challenge.challengeGroup)
            .fetchJoin() // manyToOne의 manyToOne 관계 엔티티를 가져옴
            .leftJoin(QUserChallenge.userChallenge.challengeVerifications)
            .fetchJoin() // oneToMany 관계
            .orderBy(QUserChallenge.userChallenge.id.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(userChallenges, pageable, count == null ? 0 : count);
    }

    private BooleanExpression eqUserId(Long userId) {
        return QUserChallenge.userChallenge.user.id.eq(userId);
    }

    private BooleanExpression eqProceeding() {
        return QUserChallenge.userChallenge.status.eq(ChallengeStatus.PROCEEDING);
    }

    private BooleanExpression neProceeding() {
        return QUserChallenge.userChallenge.status.ne(ChallengeStatus.PROCEEDING);
    }

    @Override
    public List<Pair<LocalDate,Integer>> countAllByUserIdAndDate(Long userId, LocalDate startDate, LocalDate endDate){
        return userChallengeRepository.countAllByUserIdAndDate(userId, startDate, endDate);
    }
}
