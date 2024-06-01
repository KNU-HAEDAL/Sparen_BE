package org.haedal.zzansuni.infrastructure.challengegroup;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
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

    @Override
    public Page<ChallengeGroup> getChallengeGroupsShortsPaging(Pageable pageable, Long userId) {
        Double seed = Double.valueOf(userId);

        // 해시 값을 기준으로 정렬
        NumberTemplate<Double> hashOrder = Expressions
                .numberTemplate(Double.class, "SHA1(CONCAT({0}, id))", seed);


        Long count = queryFactory
                .select(QChallengeGroup.challengeGroup.count())
                .from(QChallengeGroup.challengeGroup)
                .fetchOne();
        List<ChallengeGroup> page = queryFactory
                .selectFrom(QChallengeGroup.challengeGroup)
                .leftJoin(QChallengeGroup.challengeGroup.challenges).fetchJoin()
                .orderBy(Expressions.numberTemplate(Double.class, "RAND({0})", userId).asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(page, pageable, count == null ? 0 : count);
    }

}
