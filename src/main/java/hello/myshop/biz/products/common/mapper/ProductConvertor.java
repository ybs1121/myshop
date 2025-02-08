package hello.myshop.biz.products.common.mapper;


import hello.myshop.biz.products.dto.ProductRequest;
import hello.myshop.biz.products.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductConvertor {

    public Product toEntity(ProductRequest request) {
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .createdBy(request.getUserId())
                .build();
    }
}
