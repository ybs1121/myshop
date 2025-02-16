package hello.myshop.biz.cartitem.common.mapper;


import hello.myshop.biz.cartitem.dto.CartItemAdd;
import hello.myshop.biz.cartitem.dto.CartItemResponse;
import hello.myshop.biz.cartitem.entity.CartItem;
import hello.myshop.biz.products.common.mapper.ProductConvertor;
import hello.myshop.biz.products.dto.ProductRequest;
import hello.myshop.biz.products.dto.ProductResponse;
import hello.myshop.biz.products.entity.Product;
import hello.myshop.biz.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartItemConvertor {

    private final ProductConvertor productConvertor;

    public CartItem toEntity(CartItemAdd add, User user, Product product) {
        return CartItem.builder()
                .user(user)
                .product(product)
                .quantity(add.getQuantity())
                .build();
    }

    public CartItemResponse toResponse(CartItem cartItem) {
        return CartItemResponse.builder()
                .id(cartItem.getId())
                .quantity(cartItem.getQuantity())
                .product(productConvertor.toResponse(cartItem.getProduct()))
                .build();
    }
}
