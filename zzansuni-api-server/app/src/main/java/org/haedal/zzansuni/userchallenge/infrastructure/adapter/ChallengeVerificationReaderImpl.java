package org.haedal.zzansuni.userchallenge.infrastructure.adapter;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.userchallenge.domain.ChallengeVerification;
import org.haedal.zzansuni.userchallenge.domain.port.ChallengeVerificationReader;
import org.haedal.zzansuni.userchallenge.infrastructure.ChallengeVerificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import static org.haedal.zzansuni.challengegroup.domain.QChallenge.challenge;
import static org.haedal.zzansuni.challengegroup.domain.QChallengeGroup.challengeGroup;
import static org.haedal.zzansuni.userchallenge.domain.QChallengeVerification.challengeVerification;
import static org.haedal.zzansuni.userchallenge.domain.QUserChallenge.userChallenge;

@Component
@RequiredArgsConstructor
public class
ChallengeVerificationReaderImpl implements ChallengeVerificationReader {

    private final ChallengeVerificationRepository challengeVerificationRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public ChallengeVerification getById(Long id) {
        return findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Optional<ChallengeVerification> findById(Long id) {
        return challengeVerificationRepository.findById(id);
    }

    /**
     * 사용자 챌린지를 이용하여 챌린지 인증을 몇번 했는지 조회한다.
     */
    @Override
    public Integer countByUserChallengeId(Long userChallengeId) {
        return challengeVerificationRepository.countByUserChallengeId(userChallengeId);
    }

    @Override
    public List<ChallengeVerification> findByUserChallengeId(Long id) {
        return challengeVerificationRepository.findByUserChallengeId(id);
    }


    @Override
    public Page<ChallengeVerification> getVerificationOrderByCreatedAt(Pageable pageable, String challengeTitle) {
        Long count = queryFactory
                .select(challengeVerification.count())
                .from(challengeVerification)
                .innerJoin(challengeVerification.userChallenge, userChallenge)
                .innerJoin(userChallenge.challenge, challenge)
                .innerJoin(challenge.challengeGroup, challengeGroup)
                .where(eqChallengeTitle(challengeTitle))
                .fetchFirst();

        List<ChallengeVerification> userChallenges = queryFactory
                .selectFrom(challengeVerification)
                .innerJoin(challengeVerification.userChallenge, userChallenge)
                .innerJoin(userChallenge.challenge, challenge)
                .innerJoin(challenge.challengeGroup, challengeGroup)
                .where(eqChallengeTitle(challengeTitle))
                .orderBy(challengeVerification.modifiedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(userChallenges, pageable, count == null ? 0 : count);
    }

    private BooleanExpression eqChallengeTitle(String challengeTitle) {
        return challengeTitle == null ? null : challengeGroup.title.contains(challengeTitle);
    }

}
