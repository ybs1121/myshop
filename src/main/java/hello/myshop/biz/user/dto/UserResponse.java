package hello.myshop.biz.user.dto;

import hello.myshop.biz.user.common.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private long id;

    private String username;

    private String email;

    private Role role;


    @Builder
    @Data
    public static class ListResponse {
        private List<UserResponse> users;
        private long cnt;
        private long size;
        private long page;
    }
}
