package org.haedal.zzansuni.user.infrastructure;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.user.domain.User;
import org.haedal.zzansuni.user.domain.port.UserStore;
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
