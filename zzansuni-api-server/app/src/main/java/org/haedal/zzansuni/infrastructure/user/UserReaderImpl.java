package org.haedal.zzansuni.infrastructure.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.user.QUser;
import org.haedal.zzansuni.domain.user.User;
import org.haedal.zzansuni.domain.user.UserReader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserReaderImpl implements UserReader {
    private final UserRepository userRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByAuthToken(String authToken) {
        return userRepository.findByAuthToken(authToken);
    }

    @Override
    public Page<User> getUserPagingByRanking(Pageable pageable) {
        Long totalCount = queryFactory
                .select(QUser.user.count())
                .from(QUser.user)
                .fetchOne();
        List<User> users = queryFactory.selectFrom(QUser.user)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(QUser.user.exp.desc())
                .fetch();
        return new PageImpl<>(users, pageable, totalCount == null ? 0 : totalCount);
    }
}
