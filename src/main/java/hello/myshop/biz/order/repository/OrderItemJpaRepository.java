package hello.myshop.biz.order.repository;

import hello.myshop.biz.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemJpaRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrdersId(Long orderId);
}
