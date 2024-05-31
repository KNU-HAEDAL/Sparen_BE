package org.haedal.zzansuni.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserReader userReader;
    private final UserStore userStore;

    @Transactional(readOnly = true)
    public UserModel getUserModel(Long id) {
        User user = userReader.getById(id);
        return UserModel.from(user);
    }

    /**
     * 수정해야할 정보를 받고 해당 값으로 모두 업데이트
     */
    @Transactional
    public void updateUser(Long id, UserCommand.Update userUpdate) {
        User user = userReader.getById(id);
        user.update(userUpdate);
    }


    @Transactional(readOnly = true)
    public Page<UserModel> getUserPagingByRanking(Pageable pageable) {
        Page<User> users =  userReader.getUserPagingByRanking(pageable);
        return users.map(UserModel::from);
    }
}
