package hello.myshop.biz.user.dto;

import hello.myshop.biz.user.common.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private long id;

    private String username;

    private String email;

    private Role role;
}
