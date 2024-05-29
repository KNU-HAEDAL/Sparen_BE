package org.haedal.zzansuni.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserReader userReader;
    private final UserStore userStore;

    /**
     * 수정해야할 정보를 받고 해당 값으로 모두 업데이트
     */
    @Transactional
    public void updateUser(Long id, UserCommand.Update userUpdate) {
        User user = userReader.getById(id);
        user.update(userUpdate);
    }
}
