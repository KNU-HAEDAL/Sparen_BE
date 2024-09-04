package org.haedal.zzansuni.user.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.global.security.Role;
import org.haedal.zzansuni.user.domain.QUser;
import org.haedal.zzansuni.user.domain.User;
import org.haedal.zzansuni.user.domain.UserReader;
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

    @Override
    public List<User> getManagerAndAdmin() {
        return userRepository.findByRoleIn(List.of(Role.ADMIN, Role.MANAGER));
    }
}
