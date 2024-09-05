package org.haedal.zzansuni.user.domain.port;

import org.haedal.zzansuni.global.security.Role;
import org.haedal.zzansuni.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserReader {
    User getById(Long id);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByAuthToken(String authToken);

    Page<User> getUserPagingByRanking(Pageable pageable);

    List<User> getManagerAndAdmin();
}
