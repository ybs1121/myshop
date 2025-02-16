package hello.myshop.biz.cartitem.dto;

import hello.myshop.biz.products.dto.ProductResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartItemResponse {

    private Long id;

    private ProductResponse product;

    private int quantity;


    @Builder
    @Data
    public static class MainResponse {
        private List<CartItemResponse> cartItems;
        private int cnt;
    }
}
