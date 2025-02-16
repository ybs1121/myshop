package hello.myshop.biz.cartitem.dto;

import lombok.Data;

@Data
public class CartItemAdd {

    private Long userId;

    private Long productId;

    private int quantity;
}
