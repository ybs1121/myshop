package hello.myshop.biz.user.common.mapper;

import hello.myshop.biz.user.dto.UserRequest;
import hello.myshop.biz.user.dto.UserResponse;
import hello.myshop.biz.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConvertor {

    // DTO -> Entity 변환
    public User toEntity(UserRequest userRequest) {
        return User.builder()
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .role(userRequest.getRole())
                .build();
    }

    //  Entity -> DTO 변환 (필요 시)
    public UserResponse toDto(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        return response;
    }
}
