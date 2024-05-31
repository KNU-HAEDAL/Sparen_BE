package org.haedal.zzansuni.infrastructure.challengegroup.userchallenge;

import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.challengegroup.userchallenge.UserChallenge;
import org.haedal.zzansuni.domain.challengegroup.userchallenge.UserChallengeReader;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserChallengeReaderImpl implements UserChallengeReader {

    private final UserChallengeRepository userChallengeRepository;

    @Override
    public UserChallenge getById(Long id) {
        return findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Optional<UserChallenge> findById(Long id) {
        return userChallengeRepository.findById(id);
    }
}
