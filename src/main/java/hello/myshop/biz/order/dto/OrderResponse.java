package hello.myshop.biz.order.dto;

import hello.myshop.biz.order.common.constant.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private Long id;
    private BigDecimal totalPrice;
    private OrderStatus orderStatus;
    private List<OrderItemResponse> orderItems;

    @Data
    @Builder
    public static class MainResponse {
        private int cnt;
        private List<OrderResponse> orderResponseList;
    }
}
