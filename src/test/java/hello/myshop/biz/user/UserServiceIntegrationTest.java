package hello.myshop.biz.user;

import hello.myshop.biz.user.common.constant.Role;
import hello.myshop.biz.user.dto.UserRequest;
import hello.myshop.biz.user.dto.UserResponse;
import hello.myshop.biz.user.entity.User;
import hello.myshop.biz.user.repository.jpa.UserJpaRepository;
import hello.myshop.biz.user.service.UserService;
import hello.myshop.core.response.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional // 테스트 종료 후 데이터 롤백
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserJpaRepository userRepository;

    @Test
    public void registerUser_SavesToDatabase() {
        // Given: 요청 데이터 생성
        UserRequest userRequest = new UserRequest(1L, "testuser", "test@example.com", "password123", Role.ADMIN);

        // When: 회원 등록 서비스 호출
        Long userId = userService.register(userRequest);

        // Then: 데이터베이스에 저장된 데이터 확인
        User savedUser = userRepository.findById(userId).orElse(null);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("testuser");
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
    }

    @DisplayName("유저 상세 데이터 조회")
    @Test
    void getUser_SavesToDatabase() {
        // Given: 요청 데이터 생성
        UserRequest userRequest = new UserRequest(1L, "testuser", "test@example.com", "password123", Role.ADMIN);
        Long userId = userService.register(userRequest);


        // When: 회원 조회 호출
        UserResponse user = userService.getUser(userId);

        // Then: 조회 정보 확인
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("testuser");
        assertThat(user.getEmail()).isEqualTo("test@example.com");
    }

    @DisplayName("유저 업데이트")
    @Test
    void updateUser_SavesToDatabase() {
        //Given : 업데이트 정보
        UserRequest userRequest = new UserRequest(1L, "testuser", "test@example.com", "password123", Role.ADMIN);
        userService.register(userRequest);

        //When : 유저 정보 업데이트

        userRequest.setEmail("test1@example.com");
        userRequest.setUsername("testupdate");
        userRequest.setRole(Role.USER);
        userService.updateUser(userRequest);

        //Then : 유저 이름, 이메일, 역할을 바꿀 수 있다.
        User savedUser = userRepository.findById(userRequest.getId()).orElse(null);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("testupdate");
        assertThat(savedUser.getEmail()).isEqualTo("test1@example.com");
        assertThat(savedUser.getRole()).isEqualTo(Role.USER);
    }

    @DisplayName("유저 찾기 실패 에러")
    @Test
    void getUser_Fail_USER0001() {
        // GIVEN
        Long userId = -1L;

        // When
        // Then
        CustomException exception = assertThrows(CustomException.class,
                () -> userService.getUser(userId));

        assertEquals("USER0001", exception.getCode());
    }
}
