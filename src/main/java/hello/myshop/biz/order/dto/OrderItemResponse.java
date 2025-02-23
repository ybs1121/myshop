package hello.myshop.biz.order.dto;

import hello.myshop.biz.products.dto.ProductResponse;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrderItemResponse {
    private Long id;
    private ProductResponse product;
    private int quantity;
}
