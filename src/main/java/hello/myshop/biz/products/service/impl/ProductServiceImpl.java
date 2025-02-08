package hello.myshop.biz.products.service.impl;

import hello.myshop.biz.products.common.mapper.ProductConvertor;
import hello.myshop.biz.products.dto.ProductRequest;
import hello.myshop.biz.products.entity.Product;
import hello.myshop.biz.products.repository.ProductJpaRepository;
import hello.myshop.biz.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductJpaRepository productJpaRepository;
    private final ProductConvertor productConvertor;

    @Override
    public long saveProduct(ProductRequest productRequest) {
        Product product = productJpaRepository.save(productConvertor.toEntity(productRequest));
        return product.getId();
    }
}
