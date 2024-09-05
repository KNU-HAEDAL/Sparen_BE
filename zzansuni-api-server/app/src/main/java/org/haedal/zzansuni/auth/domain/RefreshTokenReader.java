package org.haedal.zzansuni.auth.domain;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenReader {
    Optional<RefreshToken> findById(String id);
}
