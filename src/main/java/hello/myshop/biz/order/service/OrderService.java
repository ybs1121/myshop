package hello.myshop.biz.order.service;

import hello.myshop.biz.order.dto.OrderItemRequest;
import hello.myshop.biz.order.dto.OrderRequest;
import hello.myshop.biz.order.dto.OrderResponse;

public interface OrderService {

    long order(OrderRequest request);

    OrderResponse.MainResponse getOrders(Long userId);

}
