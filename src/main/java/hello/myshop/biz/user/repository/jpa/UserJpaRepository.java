package hello.myshop.biz.user.repository.jpa;

import hello.myshop.biz.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
}
