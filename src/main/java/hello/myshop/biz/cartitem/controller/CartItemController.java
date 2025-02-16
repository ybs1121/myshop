package hello.myshop.biz.cartitem.controller;

import hello.myshop.biz.cartitem.dto.CartItemAdd;
import hello.myshop.biz.cartitem.dto.CartItemResponse;
import hello.myshop.biz.cartitem.service.CartItemService;
import hello.myshop.core.response.ApiResponse;
import hello.myshop.core.response.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartItemController {

    private final CartItemService cartItemService;

    //POST /api/cart: 장바구니에 상품 추가
    @PostMapping("")
    public ResponseEntity<ApiResponse<Long>> addCartItem(@RequestBody CartItemAdd add) {
        return ResponseUtil.success(cartItemService.addCartItem(add));
    }

    //DELETE /api/cart/{id}: 장바구니에서 상품 삭제
    //

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCartItem(@PathVariable Long id, Long userId) {
        return ResponseUtil.success(cartItemService.deleteCartItem(id, userId));
    }

    //GET /api/cart: 장바구니 목록 조회

    @GetMapping
    public ResponseEntity<ApiResponse<CartItemResponse.MainResponse>> getCartItems(Long userId) {
        return ResponseUtil.success(cartItemService.getCartItems(userId));
    }

}
