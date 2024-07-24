package org.haedal.zzansuni.domain.user.port;

import org.haedal.zzansuni.domain.user.User;

public interface UserStore {
    User store(User user);
}
