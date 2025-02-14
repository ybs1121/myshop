package hello.myshop.biz.products.service.impl;

import hello.myshop.biz.products.common.mapper.ProductConvertor;
import hello.myshop.biz.products.dto.ProductDelete;
import hello.myshop.biz.products.dto.ProductRequest;
import hello.myshop.biz.products.dto.ProductResponse;
import hello.myshop.biz.products.dto.ProductUpdate;
import hello.myshop.biz.products.entity.Product;
import hello.myshop.biz.products.repository.ProductJpaRepository;
import hello.myshop.biz.products.service.ProductService;
import hello.myshop.biz.user.common.constant.Role;
import hello.myshop.biz.user.entity.User;
import hello.myshop.biz.user.repository.jpa.UserJpaRepository;
import hello.myshop.core.response.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductJpaRepository productJpaRepository;
    private final ProductConvertor productConvertor;

    private final UserJpaRepository userJpaRepository;

    @Override
    public long saveProduct(ProductRequest productRequest) {
        User user = userJpaRepository.findById(productRequest.getUserId()).orElseThrow(
                () -> new CustomException("USER0001")
        );

        if (user.getRole().equals(Role.USER)) {
            throw new CustomException("USER0002");
        }

        Product product = productJpaRepository.save(productConvertor.toEntity(productRequest));
        return product.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public ProductResponse.MainResponse getProducts() {
        // TODO :: QueryDsl 동적 쿼리 만들기
        List<Product> products = productJpaRepository.findAll();
        List<ProductResponse> productResponses = products.stream().map(p -> productConvertor.toResponse(p)).collect(Collectors.toList());
        return ProductResponse.MainResponse.builder()
                .products(productResponses)
                .cnt(productResponses.size())
                .build();
    }

    @Override
    public Void updateProduct(Long id, ProductUpdate productUpdate) {

        User user = userJpaRepository.findById(productUpdate.getUserId()).orElseThrow(
                () -> new CustomException("USER0001")
        );

        if (user.getRole().equals(Role.USER)) {
            throw new CustomException("USER0002");
        }

        Product product = productJpaRepository.findById(id).orElseThrow(
                () -> new CustomException("PROD0001")
        );

        product.update(productUpdate);

        return null;
    }

    @Override
    public Void deleteProduct(Long id, ProductDelete productDelete) {

        User user = userJpaRepository.findById(productDelete.getUserId()).orElseThrow(
                () -> new CustomException("USER0001")
        );

        if (user.getRole().equals(Role.USER)) {
            throw new CustomException("USER0002");
        }

        productJpaRepository.deleteById(id);

        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public ProductResponse getProduct(Long id) {
        Product product = productJpaRepository.findById(id).orElseThrow(
                () -> new CustomException("PROD0001")
        );

        return productConvertor.toResponse(product);
    }
}
