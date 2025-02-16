package hello.myshop.biz.cartitem.service;

import hello.myshop.biz.cartitem.dto.CartItemAdd;
import hello.myshop.biz.cartitem.dto.CartItemResponse;

public interface CartItemService {
    Long addCartItem(CartItemAdd CartItemAdd);

    Void deleteCartItem(Long id, Long userId);

    CartItemResponse.MainResponse getCartItems(Long userId);


}
