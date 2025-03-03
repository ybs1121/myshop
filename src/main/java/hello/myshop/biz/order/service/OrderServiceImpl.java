package hello.myshop.biz.order.service;

import hello.myshop.biz.order.common.constant.OrderStatus;
import hello.myshop.biz.order.common.mapper.OrderConvertor;
import hello.myshop.biz.order.common.mapper.OrderItemConvertor;
import hello.myshop.biz.order.dto.OrderItemRequest;
import hello.myshop.biz.order.dto.OrderItemResponse;
import hello.myshop.biz.order.dto.OrderRequest;
import hello.myshop.biz.order.dto.OrderResponse;
import hello.myshop.biz.order.entity.Orders;
import hello.myshop.biz.order.entity.OrderItem;
import hello.myshop.biz.order.repository.OrderItemJpaRepository;
import hello.myshop.biz.order.repository.OrderJpaRepository;
import hello.myshop.biz.order.repository.OrderQueryRepository;
import hello.myshop.biz.products.entity.Product;
import hello.myshop.biz.products.repository.ProductJpaRepository;
import hello.myshop.biz.user.entity.User;
import hello.myshop.biz.user.repository.jpa.UserJpaRepository;
import hello.myshop.core.response.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderItemJpaRepository orderItemJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final ProductJpaRepository productJpaRepository;
    private final OrderConvertor orderConvertor;
    private final OrderItemConvertor orderItemConvertor;
    private final OrderQueryRepository queryRepository;

    @Override
    public long order(OrderRequest request) {
        // 사용자 검증
        if (!userJpaRepository.existsById(request.getUserId())) {
            throw new CustomException("USER0001");
        }

        // 품목 검증
        List<Long> productIdList = request.getOrderItemRequestList().stream()
                .map(OrderItemRequest::getProductId).collect(Collectors.toList());
        if (request.getOrderItemRequestList().size() != productJpaRepository.countByIdIn(productIdList)) {
            throw new CustomException("PROD0001");
        }

        // 품목 개수 확인
        List<Product> products = productJpaRepository.getProductsByIdIn(productIdList);
        Map<Long, Product> productsMap = products.stream().collect(Collectors.toMap(
                Product::getId, product -> product
        ));

        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderItemRequest orderItemRequest : request.getOrderItemRequestList()) {
            Product product = productsMap.get(orderItemRequest.getProductId());
            if (product.getStock() < orderItemRequest.getQuantity()) {
                throw new CustomException("PROD0002"); // 수량이 부족합니다.
            }

            // 수량 차감
            product.order(orderItemRequest.getQuantity());
            totalPrice = totalPrice.add(product.getPrice());
        }

        // 주문
        User user = userJpaRepository.getReferenceById(request.getUserId());
        Orders orders = orderJpaRepository.save(orderConvertor.toEntity(user, totalPrice, OrderStatus.PENDING));

        List<OrderItem> orderItemList = request.getOrderItemRequestList().stream()
                .map(o -> {
                    return orderItemConvertor.toEntity(orders, productsMap.get(o.getProductId()), o.getQuantity());
                }).collect(Collectors.toList());
        orderItemJpaRepository.saveAll(orderItemList);

        return orders.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public OrderResponse.MainResponse getOrders(Long userId) {
        List<Orders> orders = orderJpaRepository.findByUserId(userId);
        List<Long> orderIds = orders.stream().map(Orders::getId).collect(Collectors.toList());
        List<OrderItem> orderItems = orderItemJpaRepository.findByOrdersIdIn(orderIds);

//        Map<Long, Orders> orderMap = orders.stream().collect(Collectors.toMap(
//                Orders::getId, Function.identity()
//        ));

        Map<Long, List<OrderItem>> orderItemMap = orderItems.stream().collect(Collectors.groupingBy(
                orderItem -> orderItem.getOrders().getId()
        ));


        List<OrderResponse> orderResponseList = orders.stream().map(
                o -> {
                    List<OrderItem> orderItemsByOrderId = orderItemMap.get(o.getId());

                    OrderResponse orderResponse = orderConvertor.toResponse(o, orderItemsByOrderId.stream().map(oi -> orderItemConvertor.toResponse(oi))
                            .collect(Collectors.toList()));
                    return orderResponse;
                }
        ).collect(Collectors.toList());


        return OrderResponse.MainResponse.builder()
                .orderResponseList(orderResponseList)
                .cnt(orders.size())
                .build();
    }

    @Override
    public OrderItemResponse.ListResponse getOrdersV2(Long userId) {
        return queryRepository.getOrders(userId);

    }
}
