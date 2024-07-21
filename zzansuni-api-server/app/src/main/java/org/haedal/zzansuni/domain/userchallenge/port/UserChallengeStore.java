package org.haedal.zzansuni.domain.userchallenge.port;

import org.haedal.zzansuni.domain.userchallenge.UserChallenge;

public interface UserChallengeStore {

    UserChallenge store(UserChallenge userChallenge);
}
