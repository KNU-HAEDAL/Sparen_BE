package org.haedal.zzansuni.auth.infrastructure;

import org.haedal.zzansuni.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
}
