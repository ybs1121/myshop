package hello.myshop.biz.order.repository;

import hello.myshop.biz.order.entity.OrderItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemJpaRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrdersId(Long orderId);

    @EntityGraph(attributePaths = {"orders", "product"})
    List<OrderItem> findByOrdersIdIn(List<Long> orderIds);
}
