package hello.myshop.biz.products.service;

import hello.myshop.biz.products.dto.ProductRequest;
import hello.myshop.biz.products.entity.Product;

public interface ProductService {
    long saveProduct(ProductRequest productRequest);
}
