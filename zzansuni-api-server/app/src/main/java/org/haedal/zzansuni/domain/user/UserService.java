package org.haedal.zzansuni.domain.user;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.userchallenge.port.UserChallengeReader;
import org.haedal.zzansuni.domain.userchallenge.DayCountType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserReader userReader;
    private final UserChallengeReader userChallengeReader;

    @Transactional(readOnly = true)
    public UserModel.Main getUserModel(Long id) {
        User user = userReader.getById(id);
        return UserModel.Main.from(user);
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
    public Page<UserModel.Main> getUserPagingByRanking(Pageable pageable) {
        Page<User> users =  userReader.getUserPagingByRanking(pageable);
        return users.map(UserModel.Main::from);
    }

    @Transactional(readOnly = true)
    public UserModel.Strick getUserStrick(Long id, LocalDate startDate, LocalDate endDate){
        List<DayCountType> userStricks = userChallengeReader.countAllByUserIdAndDate(id, startDate, endDate);
        Map<LocalDate, Integer> map = userStricks.stream()
                .collect(Collectors.toMap(DayCountType::getDate, DayCountType::getCount));
        return UserModel.Strick.from(map, startDate, endDate);
    }
}
