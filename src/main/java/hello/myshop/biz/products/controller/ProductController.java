package hello.myshop.biz.products.controller;

import hello.myshop.biz.products.dto.ProductRequest;
import hello.myshop.biz.products.service.ProductService;
import hello.myshop.core.response.ApiResponse;
import hello.myshop.core.response.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
