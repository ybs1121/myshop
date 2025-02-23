package hello.myshop.biz.order.common.mapper;


import hello.myshop.biz.order.common.constant.OrderStatus;
import hello.myshop.biz.order.dto.OrderItemResponse;
import hello.myshop.biz.order.dto.OrderResponse;
import hello.myshop.biz.order.entity.Orders;
import hello.myshop.biz.products.common.mapper.ProductConvertor;
import hello.myshop.biz.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderConvertor {

    private final ProductConvertor productConvertor;

    public Orders toEntity(User user, BigDecimal totalPrice, OrderStatus status) {
        return Orders.builder()
                .user(user)
                .totalPrice(totalPrice)
                .orderStatus(status)
                .createdBy(user.getId())
                .build();
    }


    public OrderResponse toResponse(Orders orders, List<OrderItemResponse> orderItemResponseList) {
        return OrderResponse.builder()
                .id(orders.getId())
                .totalPrice(orders.getTotalPrice())
                .orderStatus(orders.getOrderStatus())
                .orderItems(orderItemResponseList)
                .build();
    }
}
