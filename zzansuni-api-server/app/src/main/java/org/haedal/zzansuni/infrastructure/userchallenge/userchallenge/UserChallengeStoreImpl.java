package org.haedal.zzansuni.infrastructure.userchallenge.userchallenge;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.userchallenge.UserChallenge;
import org.haedal.zzansuni.domain.userchallenge.port.UserChallengeStore;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserChallengeStoreImpl implements UserChallengeStore {

    private final UserChallengeRepository userChallengeRepository;

    @Override
    public UserChallenge store(UserChallenge userChallenge) {
        return userChallengeRepository.save(userChallenge);
    }
}
