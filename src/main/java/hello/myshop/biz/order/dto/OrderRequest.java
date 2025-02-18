package hello.myshop.biz.order.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    private Long userId;

    private List<OrderItemRequest> orderItemRequestList;


}
