package org.haedal.zzansuni.auth.infrastructure;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.auth.domain.RefreshToken;
import org.haedal.zzansuni.auth.domain.RefreshTokenReader;
import org.haedal.zzansuni.auth.domain.RefreshTokenStore;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenReaderStoreImpl implements RefreshTokenReader, RefreshTokenStore {
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken store(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);
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
