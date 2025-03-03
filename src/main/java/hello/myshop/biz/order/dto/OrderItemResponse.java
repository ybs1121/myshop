package hello.myshop.biz.order.dto;

import hello.myshop.biz.order.common.constant.OrderStatus;
import hello.myshop.biz.products.dto.ProductResponse;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
public class OrderItemResponse {
    private Long id;
    private ProductResponse product;
    private int quantity;


    @Data
    public static class OrderItemFlatResponse {
        private Long orderId;
        private Long orderItemId;
        private int quantity;
        private BigDecimal totalPrice;
        private OrderStatus orderStatus;
        private String name;
        private String description;
        private BigDecimal price;
        private Integer stock;
    }

    @Data
    @Builder
    public static class ListResponse {
        private List<OrderItemFlatResponse> orderItems;
        private long cnt;
        private long size;
        private long page;
    }
}
