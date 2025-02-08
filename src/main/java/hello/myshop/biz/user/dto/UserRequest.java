package hello.myshop.biz.user.dto;

import hello.myshop.biz.user.common.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequest {

    private String username;

    private String email;

    private String password;

    private Role role;
}
