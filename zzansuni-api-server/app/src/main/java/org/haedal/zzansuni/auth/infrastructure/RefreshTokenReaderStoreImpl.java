package org.haedal.zzansuni.auth.infrastructure;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.auth.domain.RefreshToken;
import org.haedal.zzansuni.auth.domain.RefreshTokenReader;
import org.haedal.zzansuni.auth.domain.RefreshTokenStore;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenReaderStoreImpl implements RefreshTokenReader, RefreshTokenStore {
    private final RefreshTokenRepository refreshTokenRepository;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public void flushSave(RefreshToken refreshToken) {
        entityManager.persist(refreshToken);
        entityManager.flush();
    }

    @Override
    public void delete(String  id) {
        refreshTokenRepository.deleteById(id);
    }

    @Override
    public Optional<RefreshToken> findById(String id) {
        return refreshTokenRepository.findById(id);
    }
}
