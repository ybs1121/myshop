package hello.myshop.biz.user.entity;

import hello.myshop.biz.user.common.constant.Role;
import hello.myshop.biz.user.common.convert.RoleConverter;
import hello.myshop.biz.user.dto.UserRequest;
import hello.myshop.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Convert(converter = RoleConverter.class)
    private Role role;


    public void update(UserRequest request) {
        this.username = request.getUsername();
        this.email = request.getEmail();
        this.role = request.getRole();
    }

}
