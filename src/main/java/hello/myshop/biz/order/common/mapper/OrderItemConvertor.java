package hello.myshop.biz.order.common.mapper;


import hello.myshop.biz.order.dto.OrderItemResponse;
import hello.myshop.biz.order.entity.Orders;
import hello.myshop.biz.order.entity.OrderItem;
import hello.myshop.biz.products.common.mapper.ProductConvertor;
import hello.myshop.biz.products.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemConvertor {

    private final ProductConvertor productConvertor;

    public OrderItem toEntity(Orders orders, Product product, int quantity) {
        return OrderItem.builder()
                .orders(orders)
                .product(product)
                .quantity(quantity)
                .build();
    }

    public OrderItemResponse toResponse(OrderItem orderItem) {
        return OrderItemResponse.builder()
                .id(orderItem.getId())
                .quantity(orderItem.getQuantity())
                .product(productConvertor.toResponse(orderItem.getProduct()))
                .build();
    }
}
