package hello.myshop.biz.products.service;

import hello.myshop.biz.products.dto.ProductRequest;
import hello.myshop.biz.products.dto.ProductResponse;

public interface ProductService {
    long saveProduct(ProductRequest productRequest);

    ProductResponse.MainResponse getProducts();

}
