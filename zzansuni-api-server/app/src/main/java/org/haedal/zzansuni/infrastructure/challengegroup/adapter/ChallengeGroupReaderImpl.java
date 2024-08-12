package org.haedal.zzansuni.infrastructure.challengegroup.adapter;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.challengegroup.ChallengeCategory;
import org.haedal.zzansuni.domain.challengegroup.ChallengeGroup;
import org.haedal.zzansuni.domain.challengegroup.port.ChallengeGroupReader;
import org.haedal.zzansuni.domain.user.User;
import org.haedal.zzansuni.domain.user.UserModel;
import org.haedal.zzansuni.domain.userchallenge.ChallengeGroupUserExp;
import org.haedal.zzansuni.domain.userchallenge.QChallengeGroupUserExp;
import org.haedal.zzansuni.domain.userchallenge.application.ChallengeGroupRankingModel;
import org.haedal.zzansuni.infrastructure.challengegroup.ChallengeGroupRepository;
import org.haedal.zzansuni.infrastructure.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

import static org.haedal.zzansuni.domain.challengegroup.QChallengeGroup.challengeGroup;


@Component
@RequiredArgsConstructor
public class ChallengeGroupReaderImpl implements ChallengeGroupReader {
    private final JPAQueryFactory queryFactory;
    private final ChallengeGroupRepository challengeGroupRepository;
    private final UserRepository userRepository;

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


        BooleanExpression categoryEq = category == null ?
                null : challengeGroup.category.eq(category);

        Long count = queryFactory
                .select(challengeGroup.count())
                .from(challengeGroup)
                .where(categoryEq)
                .fetchOne();

        List<Long> challengeGroupIds = queryFactory
                .select(challengeGroup.id)
                .from(challengeGroup)
                .where(categoryEq)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<ChallengeGroup> page = queryFactory
                .selectFrom(challengeGroup)
                .leftJoin(challengeGroup.challenges).fetchJoin()
                .where(challengeGroup.id.in(challengeGroupIds))
                .fetch();

        return new PageImpl<>(page, pageable, count == null ? 0 : count);
    }

    @Override
    public Page<ChallengeGroup> getChallengeGroupsShortsPaging(Pageable pageable, Long userId) {
        OrderSpecifier<Double> orderSpecifier = Expressions
                .numberTemplate(Double.class, "RAND({0})", userId)
                .asc();

        Long count = queryFactory
                .select(challengeGroup.count())
                .from(challengeGroup)
                .fetchOne();
        List<ChallengeGroup> page = queryFactory
                .selectFrom(challengeGroup)
                .leftJoin(challengeGroup.challenges).fetchJoin()
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(page, pageable, count == null ? 0 : count);
    }


    /**
     * 1. userId로 유저 엔터티 조회
     * 2. challengeGroupId, userId로 `챌린지그룹 유저 경험치`조회
     * 3. 해당 challgneGroupUserExp보다 totalExp가 큰 로우 개수 조회
     */
    @Override
    public ChallengeGroupRankingModel.Main getRanking(Long challengeGroupId, Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(NoSuchElementException::new);

        ChallengeGroupUserExp challengeGroupUserExp = queryFactory
                .select(QChallengeGroupUserExp.challengeGroupUserExp)
                .from(QChallengeGroupUserExp.challengeGroupUserExp)
                .where(
                        QChallengeGroupUserExp.challengeGroupUserExp.challengeGroup.id.eq(challengeGroupId),
                        QChallengeGroupUserExp.challengeGroupUserExp.user.id.eq(userId))
                .fetchOne();
        Integer accumulatedPoint = challengeGroupUserExp != null ? challengeGroupUserExp.getTotalExp() : 0;

        Long count = queryFactory
                .select(QChallengeGroupUserExp.challengeGroupUserExp.count())
                .from(QChallengeGroupUserExp.challengeGroupUserExp)
                .where(
                        QChallengeGroupUserExp.challengeGroupUserExp.challengeGroup.id.eq(challengeGroupId),
                        QChallengeGroupUserExp.challengeGroupUserExp.totalExp.gt(accumulatedPoint)
                )
                .fetchOne();
        assert count != null;
        Integer rank = Integer.parseInt(count.toString()) + 1;


        return ChallengeGroupRankingModel.Main.builder()
                .user(UserModel.Main.from(user))
                .rank(rank)
                .accumulatedPoint(accumulatedPoint)
                .build();
    }

}
