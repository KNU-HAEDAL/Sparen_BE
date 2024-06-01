package org.haedal.zzansuni.infrastructure.challengegroup;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.challengegroup.ChallengeCategory;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroup;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroupReader;
import org.haedal.zzansuni.domain.challengegroup.QChallengeGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class ChallengeGroupReaderImpl implements ChallengeGroupReader {
    private final JPAQueryFactory queryFactory;
    private final ChallengeGroupRepository challengeGroupRepository;
    @Override
    public ChallengeGroup getById(Long challengeGroupId) {
        return challengeGroupRepository
                .findById(challengeGroupId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public ChallengeGroup getByIdWithChallenges(Long challengeGroupId) {
        return challengeGroupRepository
                .findByIdWithChallenges(challengeGroupId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Page<ChallengeGroup> getChallengeGroupsPagingByCategory(Pageable pageable, ChallengeCategory category) {
        Long count = queryFactory
                .select(QChallengeGroup.challengeGroup.count())
                .from(QChallengeGroup.challengeGroup)
                .where(QChallengeGroup.challengeGroup.category.eq(category))
                .fetchOne();

        List<ChallengeGroup> page = queryFactory
                .selectFrom(QChallengeGroup.challengeGroup)
                .where(QChallengeGroup.challengeGroup.category.eq(category))
                .leftJoin(QChallengeGroup.challengeGroup.challenges).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(page, pageable, count == null ? 0 : count);
    }
}
