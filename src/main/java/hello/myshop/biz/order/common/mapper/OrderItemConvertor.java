package hello.myshop.biz.order.common.mapper;


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

//    public CartItemResponse toResponse(CartItem cartItem) {
//        return CartItemResponse.builder()
//                .id(cartItem.getId())
//                .quantity(cartItem.getQuantity())
//                .product(productConvertor.toResponse(cartItem.getProduct()))
//                .build();
//    }
}
