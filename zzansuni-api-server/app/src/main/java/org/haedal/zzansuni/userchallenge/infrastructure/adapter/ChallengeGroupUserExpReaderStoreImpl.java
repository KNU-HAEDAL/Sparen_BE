package org.haedal.zzansuni.userchallenge.infrastructure.adapter;

import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.userchallenge.domain.ChallengeGroupUserExp;
import org.haedal.zzansuni.userchallenge.domain.port.ChallengeGroupUserExpReader;
import org.haedal.zzansuni.userchallenge.domain.port.ChallengeGroupUserExpStore;
import org.haedal.zzansuni.userchallenge.infrastructure.ChallengeGroupUserExpRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static org.haedal.zzansuni.userchallenge.domain.QChallengeGroupUserExp.challengeGroupUserExp;

@Component
@RequiredArgsConstructor
public class ChallengeGroupUserExpReaderStoreImpl implements ChallengeGroupUserExpReader, ChallengeGroupUserExpStore {
    private final ChallengeGroupUserExpRepository challengeGroupUserExpRepository;
    private final JPQLQueryFactory queryFactory;

    @Override
    public ChallengeGroupUserExp store(ChallengeGroupUserExp challengeGroupUserExp) {
        return challengeGroupUserExpRepository.save(challengeGroupUserExp);
    }

    @Override
    public Optional<ChallengeGroupUserExp> findByChallengeGroupIdAndUserId(Long challengeGroupId, Long userId) {
        ChallengeGroupUserExp result = queryFactory
                .selectFrom(challengeGroupUserExp)
                .where(challengeGroupUserExp.challengeGroup.id.eq(challengeGroupId)
                        .and(challengeGroupUserExp.user.id.eq(userId)))
                .fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public Page<ChallengeGroupUserExp> getUserExpPagingWithUserByChallengeGroupId(Long challengeGroupId, Pageable pageable) {
        Long count = queryFactory
                .select(challengeGroupUserExp.count())
                .from(challengeGroupUserExp)
                .where(challengeGroupUserExp.challengeGroup.id.eq(challengeGroupId))
                .fetchOne();

        List<ChallengeGroupUserExp> page = queryFactory
                .selectFrom(challengeGroupUserExp)
                .where(challengeGroupUserExp.challengeGroup.id.eq(challengeGroupId))
                .innerJoin(challengeGroupUserExp.user).fetchJoin()
                .orderBy(challengeGroupUserExp.totalExp.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(page, pageable, count == null ? 0 : count);
    }
}
