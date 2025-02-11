package hello.myshop.biz.products;

import hello.myshop.biz.products.dto.ProductRequest;
import hello.myshop.biz.products.dto.ProductResponse;
import hello.myshop.biz.products.service.ProductService;
import hello.myshop.biz.user.common.constant.Role;
import hello.myshop.biz.user.dto.UserRequest;
import hello.myshop.biz.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;


    @Test
    void 품목_리스트_호출_테스트() throws Exception {
        //Given
        UserRequest userRequest = new UserRequest(1L, "testuser", "test@example.com", "password123", Role.ADMIN);

        long register = userService.register(userRequest);
        ProductRequest productRequest = makeProductRequestSample();
        productRequest.setUserId(register);
        productService.saveProduct(productRequest);

        //When
        ProductResponse.MainResponse products = productService.getProducts();

        //Then
        Assertions.assertThat(products).isNotNull();
        Assertions.assertThat(products.getProducts()).isNotEmpty();
        Assertions.assertThat(products.getProducts()).hasSize(1);

        ProductResponse productResponse = products.getProducts().get(0);
        Assertions.assertThat(productResponse.getDescription()).isEqualTo(productRequest.getDescription());
        Assertions.assertThat(productResponse.getName()).isEqualTo(productRequest.getName());
        Assertions.assertThat(productResponse.getStock()).isEqualTo(productRequest.getStock());
    }


    private ProductRequest makeProductRequestSample() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setUserId(1L);
        productRequest.setName("TV");
        productRequest.setDescription("엄청난 고화질의 티비");
        productRequest.setStock(100);
        productRequest.setPrice(new BigDecimal("100.00"));
        return productRequest;
    }

}
