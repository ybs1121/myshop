package hello.myshop.biz.user.entity;

import hello.myshop.biz.user.common.constant.Role;
import hello.myshop.biz.user.common.convert.RoleConverter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class User {

    @Id
    @GeneratedValue
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


}
