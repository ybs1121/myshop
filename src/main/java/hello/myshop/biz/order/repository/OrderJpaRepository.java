package hello.myshop.biz.order.repository;

import hello.myshop.biz.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderJpaRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUserId(Long userId);
}
