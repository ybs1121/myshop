package hello.myshop.biz.products.service;

import hello.myshop.biz.products.dto.ProductDelete;
import hello.myshop.biz.products.dto.ProductRequest;
import hello.myshop.biz.products.dto.ProductResponse;
import hello.myshop.biz.products.dto.ProductUpdate;

public interface ProductService {
    long saveProduct(ProductRequest productRequest);

    ProductResponse.MainResponse getProducts();

    Void updateProduct(Long id, ProductUpdate productUpdate);

    Void deleteProduct(Long id, ProductDelete productDelete);

    ProductResponse getProduct(Long id);
}
