package hello.myshop.biz.order.controller;

import hello.myshop.biz.order.dto.OrderRequest;
import hello.myshop.biz.order.dto.OrderResponse;
import hello.myshop.biz.order.service.OrderService;
import hello.myshop.biz.products.dto.ProductResponse;
import hello.myshop.core.response.ApiResponse;
import hello.myshop.core.response.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<Long>> getProducts(OrderRequest request) {
        return ResponseUtil.success(orderService.order(request));
    }

    @GetMapping("")
    public  ResponseEntity<ApiResponse<OrderResponse.MainResponse>> getProducts(Long userId) {
        return ResponseUtil.success(orderService.getOrders(userId));
    }
}
