package org.haedal.zzansuni.infrastructure.userchallenge.adapter;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.userchallenge.UserChallenge;
import org.haedal.zzansuni.domain.userchallenge.port.UserChallengeStore;
import org.haedal.zzansuni.infrastructure.userchallenge.UserChallengeRepository;
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
