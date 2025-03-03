package hello.myshop.biz.order.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.myshop.biz.order.dto.OrderItemResponse;
import hello.myshop.biz.order.dto.OrderResponse;
import hello.myshop.biz.order.entity.QOrderItem;
import hello.myshop.biz.order.entity.QOrders;
import hello.myshop.biz.products.entity.QProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Repository
public class OrderQueryRepositoryImpl implements OrderQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public OrderItemResponse.ListResponse getOrders(Long userId) {
        QOrders orders = QOrders.orders;
        QOrderItem orderItem = QOrderItem.orderItem;
        QProduct product = QProduct.product;


        List<OrderItemResponse.OrderItemFlatResponse> orderItemFlatResponseList = queryFactory.select(
                        Projections.fields(
                                OrderItemResponse.OrderItemFlatResponse.class,
                                orders.id.as("orderId"),
                                orderItem.id.as("orderItemId"),
                                orderItem.quantity,
                                orders.totalPrice,
                                orders.orderStatus,
                                product.name,
                                product.description,
                                product.price,
                                product.stock
                        )
                ).from(orderItem)
                .leftJoin(orders).on(orders.id.eq(orderItem.orders.id))
                .leftJoin(product).on(product.id.eq(orderItem.product.id))
                .where(orders.user.id.eq(userId))
                .orderBy(orders.id.desc())
                .fetch();


        return OrderItemResponse.ListResponse.builder()
                .orderItems(orderItemFlatResponseList)
                .cnt(orderItemFlatResponseList.size())
                .build();
    }
}
