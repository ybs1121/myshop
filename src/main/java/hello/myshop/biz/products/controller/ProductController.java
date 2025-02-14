package hello.myshop.biz.products.controller;

import hello.myshop.biz.products.dto.ProductDelete;
import hello.myshop.biz.products.dto.ProductRequest;
import hello.myshop.biz.products.dto.ProductResponse;
import hello.myshop.biz.products.dto.ProductUpdate;
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

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProduct(@PathVariable Long id
    ) {
        return ResponseUtil.success(productService.getProduct(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateProduct(@PathVariable Long id, @RequestBody ProductUpdate productUpdate) {

        return ResponseUtil.success(productService.updateProduct(id, productUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id, @RequestBody ProductDelete productDelete) {

        return ResponseUtil.success(productService.deleteProduct(id, productDelete));
    }
}
