package org.haedal.zzansuni.domain.user;

import java.util.Optional;

public interface UserReader {
    User getById(Long id);
    Optional<User> getByAuthToken(String authToken);
}
