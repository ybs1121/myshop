package hello.myshop.biz.products.controller;

import hello.myshop.biz.products.dto.ProductRequest;
import hello.myshop.biz.products.dto.ProductResponse;
import hello.myshop.biz.products.service.ProductService;
import hello.myshop.core.response.ApiResponse;
import hello.myshop.core.response.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    // 저장
    @PostMapping("")
    public ResponseEntity<ApiResponse<Long>> saveProduct(@RequestBody ProductRequest productRequest) {
        return ResponseUtil.success(productService.saveProduct(productRequest));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<ProductResponse.MainResponse>> getProducts(
            ) {
        return ResponseUtil.success(productService.getProducts());
    }
}
