package hello.myshop.biz.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.myshop.biz.user.common.constant.Role;
import hello.myshop.biz.user.dto.UserRequest;
import hello.myshop.biz.user.dto.UserResponse;
import hello.myshop.biz.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.module.ResolutionException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class) // UserController만 로드
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService; // Service를 Mock으로 대체

    @Autowired
    private ObjectMapper objectMapper; // JSON 변환용

    @DisplayName("유저 등록 호출 성공")
    @Test
    public void registerUser_Success() throws Exception {
        // Given: 요청 데이터와 Service 동작 정의
        UserRequest userRequest = new UserRequest(1L, "testuser", "test@example.com", "password123", Role.ADMIN);
        when(userService.register(userRequest)).thenReturn(1L); // Mock 동작 정의

        // When: POST 요청 수행
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest))) // JSON 요청 본문
                .andExpect(status().isOk()) // HTTP 상태 코드 검증
                .andExpect(jsonPath("$.data").value(1L)) // 응답 데이터 검증
                .andExpect(jsonPath("$.status").value("success")); // 메시지 검증
    }

    @DisplayName("유저 조회 성공")
    @Test
    public void getUser_Success() throws Exception {
        //Given : 응답 데이터 세팅
        UserResponse userResponse = new UserResponse(1L, "joy", "test@example.com", Role.ADMIN);
        when(userService.getUser(1L)).thenReturn(userResponse);

        mockMvc.perform(get("/api/users/profile/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.username").value("joy"))
                .andExpect(jsonPath("$.status").value("success"));
    }


//    @Test
//    public void registerUser_InvalidInput() throws Exception {
//        // Given: 잘못된 요청 데이터
//        UserRequest invalidRequest = new UserRequest(1L, "test", "invalid-email", "", null);
//
//        // When: POST 요청 수행
//        mockMvc.perform(post("/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(invalidRequest)))
//                .andExpect(status().isBadRequest()); // 유효성 검증 실패 확인
//    }
}
