package org.haedal.zzansuni.infrastructure.challengegroup;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.challengegroup.*;
import org.haedal.zzansuni.domain.challengegroup.userexp.ChallengeGroupUserExp;
import org.haedal.zzansuni.domain.challengegroup.userexp.QChallengeGroupUserExp;
import org.haedal.zzansuni.domain.user.QUser;
import org.haedal.zzansuni.domain.user.User;
import org.haedal.zzansuni.domain.user.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.haedal.zzansuni.domain.challengegroup.QChallengeGroup.challengeGroup;
import static org.haedal.zzansuni.domain.challengegroup.userexp.QChallengeGroupUserExp.challengeGroupUserExp;

@Component
@RequiredArgsConstructor
public class ChallengeGroupReaderImpl implements ChallengeGroupReader {
    private final JPAQueryFactory queryFactory;
    private final JdbcTemplate jdbcTemplate;
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
                .select(challengeGroup.count())
                .from(challengeGroup)
                .where(challengeGroup.category.eq(category))
                .fetchOne();

        List<ChallengeGroup> page = queryFactory
                .selectFrom(challengeGroup)
                .where(challengeGroup.category.eq(category))
                .leftJoin(challengeGroup.challenges).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
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

    @Override
    public ChallengeGroupModel.Ranking getRanking(Long challengeGroupId, Long userId) {
        User user = queryFactory
                .selectFrom(QUser.user)
                .where(QUser.user.id.eq(userId))
                .fetchOne();
        if(user == null) {
            throw new NoSuchElementException();
        }

        String sql = "SELECT ranked.rank1 FROM (" +
                "  SELECT user_id, RANK() OVER (ORDER BY total_exp DESC) as rank1 " +
                "  FROM challenge_group_user_exp " +
                "  WHERE challenge_group_id = ?" +
                ") as ranked " +
                "WHERE ranked.user_id = ?";

        Integer rank = jdbcTemplate.queryForObject(sql, Integer.class, challengeGroupId, userId);


        ChallengeGroupUserExp challengeGroupUserExp = queryFactory
                .select(QChallengeGroupUserExp.challengeGroupUserExp)
                .from(QChallengeGroupUserExp.challengeGroupUserExp)
                .where(QChallengeGroupUserExp.challengeGroupUserExp.challengeGroup.id.eq(challengeGroupId)
                        .and(QChallengeGroupUserExp.challengeGroupUserExp.user.id.eq(userId)))
                .fetchOne();

        return ChallengeGroupModel.Ranking.builder()
                .rank(rank==null ? 0 : rank)
                .accumulatedPoint(challengeGroupUserExp != null ? challengeGroupUserExp.getTotalExp() : 0)
                .user(UserModel.Main.from(user))
                .build();
    }

}
