package hello.myshop.biz.products;

import hello.myshop.biz.products.dto.ProductDelete;
import hello.myshop.biz.products.dto.ProductRequest;
import hello.myshop.biz.products.dto.ProductResponse;
import hello.myshop.biz.products.dto.ProductUpdate;
import hello.myshop.biz.products.entity.Product;
import hello.myshop.biz.products.repository.ProductJpaRepository;
import hello.myshop.biz.products.service.ProductService;
import hello.myshop.biz.user.common.constant.Role;
import hello.myshop.biz.user.dto.UserRequest;
import hello.myshop.biz.user.service.UserService;
import hello.myshop.core.response.CustomException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
public class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductJpaRepository productJpaRepository;

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

    @Test
    void 품목_업데이트() throws Exception {
        // Given
        UserRequest userRequest = new UserRequest(1L, "testuser", "test@example.com", "password123", Role.ADMIN);
        long register = userService.register(userRequest);

        ProductRequest productRequest = makeProductRequestSample();
        productRequest.setUserId(register);

        long id = productService.saveProduct(productRequest);

        ProductUpdate update = new ProductUpdate();
        update.setDescription("설명");
        update.setStock(10);
        update.setName("엄청난 슈퍼 고화질의 티비");
        update.setPrice(new BigDecimal("101.00"));


        //When
        productService.updateProduct(id, update);

        // Then
        Product findProduct = productJpaRepository.findById(id).get();
        Assertions.assertThat(findProduct.getPrice()).isEqualTo(update.getPrice());
        Assertions.assertThat(findProduct.getName()).isEqualTo(update.getName());
        Assertions.assertThat(findProduct.getDescription()).isEqualTo(update.getDescription());
        Assertions.assertThat(findProduct.getStock()).isEqualTo(update.getStock());
    }

    @Test
    void 품목_삭제() throws Exception {
        // Given
        UserRequest userRequest = new UserRequest(1L, "testuser", "test@example.com", "password123", Role.ADMIN);
        long register = userService.register(userRequest);

        ProductRequest productRequest = makeProductRequestSample();
        productRequest.setUserId(register);

        long id = productService.saveProduct(productRequest);
        ProductDelete productDelete = new ProductDelete();
        productDelete.setId(id);
        productDelete.setUserId(register);
        // When
        productService.deleteProduct(id, productDelete);

        //Then
        CustomException customException = Assertions.catchThrowableOfType(() -> {
            productService.getProduct(id);
        }, CustomException.class);

        //PROD0001=품목이 존재하지 않습니다.
        Assertions.assertThat(customException.getCode()).isEqualTo("PROD0001");

        //MessageProperties 에서 메시지 꺼내서 set 해주는 거라 처음에는 메세지가 없다
//        Assertions.assertThat(customException.getErrorMsg()).containsIgnoringCase("품목이 존재하지 않습니다.");

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
