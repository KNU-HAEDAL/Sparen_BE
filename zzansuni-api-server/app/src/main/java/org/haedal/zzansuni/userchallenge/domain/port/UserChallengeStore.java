package org.haedal.zzansuni.userchallenge.domain.port;

import org.haedal.zzansuni.userchallenge.domain.UserChallenge;

public interface UserChallengeStore {

    UserChallenge store(UserChallenge userChallenge);
}
