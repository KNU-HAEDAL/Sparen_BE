package org.haedal.zzansuni.user.infrastructure;

import org.haedal.zzansuni.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAuthToken(String authToken);
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
