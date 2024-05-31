package org.haedal.zzansuni.infrastructure.user;

import org.haedal.zzansuni.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAuthToken(String authToken);
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
