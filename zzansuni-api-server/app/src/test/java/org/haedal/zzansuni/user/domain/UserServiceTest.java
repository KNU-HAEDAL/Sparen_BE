package org.haedal.zzansuni.user.domain;

import org.haedal.zzansuni.global.security.Role;
import org.haedal.zzansuni.user.domain.port.UserReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserReader userReader;


    @DisplayName("사용자 정보를 조회한다.")
    @Test
    public void getUserModel(){
        //given
        when(userReader.getById(1L)).thenReturn(createUser(1L,"테스트유저1"));

        //when
        UserModel.Main userMain = userService.getUserModel(1L);

        //then
        assertNotNull(userMain);
        assertThat(userMain.id()).isEqualTo(1L);
        assertThat(userMain.nickname()).isEqualTo("테스트유저1");
    }


    @DisplayName("존재하지 않는 id로 사용자 정보를 조회한경우 예외를 던진다.")
    @Test
    public void getUserModelNonExist(){
        //given
        when(userReader.getById(0L)).thenThrow(new NoSuchElementException());

        //when
        //then
        assertThrows(NoSuchElementException.class, () -> userService.getUserModel(0L));
    }


    @DisplayName("사용자 정보를 수정한다.")
    @Test
    public void updateUser(){
        //given
        User user = createUser(1L,"테스트유저");

        //when
        user.update(new UserCommand.Update("업데이트된 유저"));

        //then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getNickname()).isEqualTo("업데이트된 유저");
    }


    @DisplayName("전체 사용자 랭킹을 조회한다.")
    @Test
    public void getUserPagingByRanking(){
        //given
        Pageable pageable = Pageable.ofSize(5).withPage(0);
        List<User> userList = createUsers();
        when(userReader.getUserPagingByRanking(pageable)).thenReturn(new PageImpl<>(userList, pageable, userList.size()));

        //when
        Page<UserModel.Main> userPage = userService.getUserPagingByRanking(pageable);


        //then
        assertThat(userPage.getTotalElements()).isEqualTo(userList.size());
        assertThat(userPage.hasNext()).isEqualTo(true);
    }


//    @DisplayName("유효하지 않는 페이지로 전체 사용자 랭킹을 조회하면 예외를 던진다.")
//    @Test
    public void getUserPagingByRankingWithInvalidPage(){
        //given
        Pageable pageable = Pageable.ofSize(5).withPage(2);
        when(userReader.getUserPagingByRanking(pageable)).thenThrow(new IllegalStateException());

        //when
        //then
        assertThrows(IllegalStateException.class, () -> userService.getUserPagingByRanking(pageable));
    }



    @DisplayName("전체 사용자 랭킹이 내림차순으로 나타난다.")
    @Test
    public void getUserPagingByRankingOrder(){

    }


    @DisplayName("유저의 스트릭을 조회한다.")
    @Test
    public void getUserStrick(){

    }



    User createUser(Long id, String nickname){
        return User.builder()
                .id(id)
                .nickname(nickname)
                .email(null)
                .password(null)
                .role(Role.USER)
                .provider(null)
                .authToken(null)
                .exp(0)
                .profileImageUrl(null)
                .build();
    }

    List<User> createUsers(){
        return Stream.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L)
                .map(i -> createUser(i, "user" + i))
                .collect(Collectors.toList());
    }
}
