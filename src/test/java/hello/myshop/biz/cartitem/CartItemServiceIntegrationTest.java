package hello.myshop.biz.cartitem;


import hello.myshop.biz.cartitem.dto.CartItemAdd;
import hello.myshop.biz.cartitem.dto.CartItemResponse;
import hello.myshop.biz.cartitem.entity.CartItem;
import hello.myshop.biz.cartitem.repository.CartItemJpaRepository;
import hello.myshop.biz.cartitem.service.CartItemService;
import hello.myshop.biz.products.dto.ProductRequest;
import hello.myshop.biz.products.dto.ProductResponse;
import hello.myshop.biz.products.entity.Product;
import hello.myshop.biz.products.repository.ProductJpaRepository;
import hello.myshop.biz.products.service.ProductService;
import hello.myshop.biz.user.common.constant.Role;
import hello.myshop.biz.user.dto.UserRequest;
import hello.myshop.biz.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Transactional
@SpringBootTest
public class CartItemServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CartItemJpaRepository cartItemJpaRepository;

    private Long userId;
    private Long productId1;
    private Long productId2;
    @Autowired
    private ProductJpaRepository productJpaRepository;

    @BeforeEach
    public void init() {
        // 사용자 등록
        UserRequest userRequest = new UserRequest(1L, "testuser", "test@example.com", "password123", Role.ADMIN);
        userId = userService.register(userRequest);

        // 품목 등록
        ProductRequest productRequest = new ProductRequest();
        productRequest.setUserId(userId);
        productRequest.setName("TV");
        productRequest.setDescription("엄청난 고화질의 티비");
        productRequest.setStock(100);
        productRequest.setPrice(new BigDecimal("100.00"));
        productId1 = productService.saveProduct(productRequest);

        ProductRequest productRequest2 = new ProductRequest();
        productRequest2.setUserId(userId);
        productRequest2.setName("컴퓨터");
        productRequest2.setDescription("슈퍼컴퓨터");
        productRequest2.setStock(50);
        productRequest2.setPrice(new BigDecimal("130.00"));
        productId2 = productService.saveProduct(productRequest2);

    }

    @Test
    void 장바구니_아이템_추가() {
        //Given
        CartItemAdd cartItemAdd = new CartItemAdd();
        cartItemAdd.setProductId(productId1);
        cartItemAdd.setQuantity(1);
        cartItemAdd.setUserId(userId);

        //When
        Long cartItemId = cartItemService.addCartItem(cartItemAdd);

        //Then
        CartItem cartItem = cartItemJpaRepository.findById(cartItemId).get();

        Assertions.assertThat(cartItem.getQuantity()).isEqualTo(cartItemAdd.getQuantity());
        Assertions.assertThat(cartItem.getUser().getId()).isEqualTo(userId);
        Assertions.assertThat(cartItem.getProduct().getId()).isEqualTo(productId1);

    }

    @Test
    void 장바구니_아이템_삭제() throws Exception {
        //given
        CartItemAdd cartItemAdd = new CartItemAdd();
        cartItemAdd.setProductId(productId1);
        cartItemAdd.setQuantity(1);
        cartItemAdd.setUserId(userId);
        Long cartItemId = cartItemService.addCartItem(cartItemAdd);

        //when
        cartItemService.deleteCartItem(cartItemId, userId);

        //then
        boolean exists = cartItemJpaRepository.existsByIdAndUserId(cartItemId, userId);
        Assertions.assertThat(exists).isFalse();
    }

    @Test
    void 장바구니_목록_조회() throws Exception {
        //given
        CartItemAdd cartItemAdd = new CartItemAdd();
        cartItemAdd.setProductId(productId1);
        cartItemAdd.setQuantity(1);
        cartItemAdd.setUserId(userId);
        Long cartItemId = cartItemService.addCartItem(cartItemAdd);

        //when
        CartItemResponse.MainResponse cartItems = cartItemService.getCartItems(userId);

        //then
        Assertions.assertThat(cartItems.getCnt()).isEqualTo(1);
        List<CartItemResponse> cartItemsResponseList = cartItems.getCartItems();
        Assertions.assertThat(cartItemsResponseList).isNotNull();
        Assertions.assertThat(cartItemsResponseList).isNotEmpty();
        Assertions.assertThat(cartItemsResponseList.size()).isEqualTo(cartItems.getCnt());

        ProductResponse product = cartItemsResponseList.get(0).getProduct();
        Product product1 = productJpaRepository.findById(productId1).get();
        Product product2 = productJpaRepository.findById(productId2).get();

        Assertions.assertThat(product).isNotNull();

        Assertions.assertThat(product).satisfiesAnyOf(
                value -> Assertions.assertThat(value.getName()).isEqualTo(product1.getName()),
                value -> Assertions.assertThat(value.getName()).isEqualTo(product2.getName())
        );


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
