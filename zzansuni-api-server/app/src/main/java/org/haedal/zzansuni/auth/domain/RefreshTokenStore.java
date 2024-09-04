package org.haedal.zzansuni.auth.domain;

public interface RefreshTokenStore {
    RefreshToken store(RefreshToken refreshToken);

    void delete(String id);
}
