package org.haedal.zzansuni.auth.domain;


public interface RefreshTokenStore {
    void flushSave(RefreshToken refreshToken);

    void delete(String id);
}
