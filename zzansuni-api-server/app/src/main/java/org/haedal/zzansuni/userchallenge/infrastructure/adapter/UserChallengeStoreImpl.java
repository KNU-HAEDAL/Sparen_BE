package org.haedal.zzansuni.userchallenge.infrastructure.adapter;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.userchallenge.domain.UserChallenge;
import org.haedal.zzansuni.userchallenge.domain.port.UserChallengeStore;
import org.haedal.zzansuni.userchallenge.infrastructure.UserChallengeRepository;
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
