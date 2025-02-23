package hello.myshop.biz.products.hepler;

import hello.myshop.biz.products.entity.Product;
import hello.myshop.biz.products.repository.ProductJpaRepository;
import hello.myshop.core.response.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductHelper {

    private final ProductJpaRepository productJpaRepository;


    public void validateProductExist(Long productId) {
        if (!productJpaRepository.existsById(productId)) {
            throw new CustomException("PROD0001");
        }
    }

    public Product getProductReferenceById(Long productId) {
        return productJpaRepository.getReferenceById(productId);
    }


}
