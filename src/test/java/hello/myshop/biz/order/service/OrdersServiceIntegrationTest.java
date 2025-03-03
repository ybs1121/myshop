package hello.myshop.biz.order.service;

import hello.myshop.biz.cartitem.repository.CartItemJpaRepository;
import hello.myshop.biz.order.common.constant.OrderStatus;
import hello.myshop.biz.order.dto.OrderItemRequest;
import hello.myshop.biz.order.dto.OrderItemResponse;
import hello.myshop.biz.order.dto.OrderRequest;
import hello.myshop.biz.order.dto.OrderResponse;
import hello.myshop.biz.order.entity.Orders;
import hello.myshop.biz.order.entity.OrderItem;
import hello.myshop.biz.order.repository.OrderItemJpaRepository;
import hello.myshop.biz.order.repository.OrderJpaRepository;
import hello.myshop.biz.products.dto.ProductRequest;
import hello.myshop.biz.products.entity.Product;
import hello.myshop.biz.products.repository.ProductJpaRepository;
import hello.myshop.biz.products.service.ProductService;
import hello.myshop.biz.user.common.constant.Role;
import hello.myshop.biz.user.dto.UserRequest;
import hello.myshop.biz.user.service.UserService;
import hello.myshop.core.response.CustomException;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class OrdersServiceIntegrationTest {


    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;


    private Long userId;
    private Long productId1;
    private Long productId2;
    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private OrderJpaRepository orderJpaRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartItemJpaRepository cartItemRepository;
    @Autowired
    private OrderItemJpaRepository orderItemJpaRepository;

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
    void 주문_성공_테스트() {
        //Given
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUserId(userId);
        List<OrderItemRequest> orderItemRequestList = new ArrayList<>();
        OrderItemRequest orderItemRequest = new OrderItemRequest();
        orderItemRequest.setProductId(productId1);
        orderItemRequest.setQuantity(1);
        orderItemRequestList.add(orderItemRequest);
        orderRequest.setOrderItemRequestList(orderItemRequestList);

        //When
        long orderId = orderService.order(orderRequest);

        //Then
        Product product = productJpaRepository.findById(productId1).get();
        Assertions.assertThat(product.getStock()).isEqualTo(99);

        Orders orders = orderJpaRepository.findById(orderId).get();
        Assertions.assertThat(orders.getOrderStatus()).isEqualTo(OrderStatus.PENDING);
        Assertions.assertThat(orders.getTotalPrice()).isEqualTo(new BigDecimal("100.00"));

        List<OrderItem> orderItemList = orderItemJpaRepository.findByOrdersId(orderId);
        Assertions.assertThat(orderItemList.size()).isEqualTo(1);
        Assertions.assertThat(orderItemList.get(0).getProduct().getId()).isEqualTo(product.getId());
    }

    @Test
    void 주문_실패_수량초과_테스트() {
        //Given
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUserId(userId);
        List<OrderItemRequest> orderItemRequestList = new ArrayList<>();
        OrderItemRequest orderItemRequest = new OrderItemRequest();
        orderItemRequest.setProductId(productId1);
        orderItemRequest.setQuantity(100);
        orderItemRequestList.add(orderItemRequest);
        orderRequest.setOrderItemRequestList(orderItemRequestList);

        //When
        long orderId = orderService.order(orderRequest);

//        Assertions.assertThatThrownBy(() -> orderService.order(orderRequest))
//                .isInstanceOf(CustomException.class);

        CustomException customException = assertThrows(CustomException.class,
                () -> orderService.order(orderRequest));

        Assertions.assertThat(customException.getCode()).isEqualTo("PROD0002");
    }

    @Test
    void 주문_조회_성공_테스트() {
        // Given
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUserId(userId);
        List<OrderItemRequest> orderItemRequestList = new ArrayList<>();
        OrderItemRequest orderItemRequest = new OrderItemRequest();
        orderItemRequest.setProductId(productId1);
        orderItemRequest.setQuantity(1);
        orderItemRequestList.add(orderItemRequest);
        orderRequest.setOrderItemRequestList(orderItemRequestList);
        orderService.order(orderRequest);

        //When
        OrderResponse.MainResponse orders = orderService.getOrders(orderRequest.getUserId());

        //Then

        Assertions.assertThat(orders.getCnt()).isEqualTo(1);
        Assertions.assertThat(orders.getOrderResponseList().size()).isEqualTo(1);

    }

    @Test
    void 주문조회_테스트_V2() {
        // Given
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUserId(userId);
        List<OrderItemRequest> orderItemRequestList = new ArrayList<>();
        OrderItemRequest orderItemRequest = new OrderItemRequest();
        orderItemRequest.setProductId(productId1);
        orderItemRequest.setQuantity(1);
        orderItemRequestList.add(orderItemRequest);
        orderRequest.setOrderItemRequestList(orderItemRequestList);
        long order = orderService.order(orderRequest);

        //When
        OrderItemResponse.ListResponse orders = orderService.getOrdersV2(orderRequest.getUserId());

        //Then

        Assertions.assertThat(orders.getCnt()).isEqualTo(1);
        Assertions.assertThat(orders.getOrderItems())
                .isNotEmpty()
                .hasSize(1)
                .extracting("orderId", "quantity", "name")
                .containsExactlyInAnyOrder(
                        Tuple.tuple(order, 1, "TV")
                );

    }
}