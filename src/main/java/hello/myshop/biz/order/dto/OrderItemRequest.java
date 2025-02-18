package hello.myshop.biz.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemRequest {

    @NotNull
    private Long productId;

    @NotNull
    private Integer quantity;

}
