package hello.myshop.biz.order.repository;

import hello.myshop.biz.order.dto.OrderItemResponse;

public interface OrderQueryRepository {

    OrderItemResponse.ListResponse getOrders(Long userId);
}
