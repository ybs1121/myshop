package hello.myshop.biz.order.service;

import hello.myshop.biz.order.dto.OrderItemRequest;
import hello.myshop.biz.order.dto.OrderRequest;

public interface OrderService {

    long order(OrderRequest request);
}
