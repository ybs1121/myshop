package hello.myshop.biz.order.repository;

import hello.myshop.biz.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Orders, Long> {
}
