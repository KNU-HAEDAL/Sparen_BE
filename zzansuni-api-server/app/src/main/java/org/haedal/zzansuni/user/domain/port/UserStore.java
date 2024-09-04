package org.haedal.zzansuni.user.domain.port;

import org.haedal.zzansuni.user.domain.User;

public interface UserStore {
    User store(User user);
}
