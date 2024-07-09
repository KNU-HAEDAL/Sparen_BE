package org.haedal.zzansuni.domain.user;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.haedal.zzansuni.domain.challengegroup.userchallenge.UserChallengeReader;
import org.haedal.zzansuni.domain.challengegroup.verification.ChallengeVerificationReader;
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
    private final ChallengeVerificationReader challengeVerificationReader;

    @Transactional(readOnly = true)
    public UserModel.Info getUserModel(Long id) {
        User user = userReader.getById(id);
        return UserModel.Info.from(user);
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
    public Page<UserModel.Info> getUserPagingByRanking(Pageable pageable) {
        Page<User> users =  userReader.getUserPagingByRanking(pageable);
        return users.map(UserModel.Info::from);
    }

    @Transactional(readOnly = true)
    public UserModel.Strick getUserStrick(Long id, LocalDate startDate, LocalDate endDate){
        List<Pair<LocalDate,Integer>> userStricks = userChallengeReader.findAllByUserIdAndCreatedAt(id, startDate, endDate);
        Map<LocalDate, Integer> map = userStricks.stream()
                .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
        return UserModel.Strick.from(map, startDate, endDate);
    }
}
