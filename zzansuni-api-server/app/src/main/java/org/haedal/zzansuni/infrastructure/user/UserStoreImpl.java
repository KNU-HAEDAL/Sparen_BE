package org.haedal.zzansuni.infrastructure.user;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.user.User;
import org.haedal.zzansuni.domain.user.UserStore;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserStoreImpl implements UserStore {
    private final UserRepository userRepository;

    @Override
    public User store(User user) {
        return userRepository.save(user);
    }
}
