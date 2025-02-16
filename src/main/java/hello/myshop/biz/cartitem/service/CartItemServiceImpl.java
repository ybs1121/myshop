package hello.myshop.biz.cartitem.service;

import hello.myshop.biz.cartitem.common.mapper.CartItemConvertor;
import hello.myshop.biz.cartitem.dto.CartItemAdd;
import hello.myshop.biz.cartitem.dto.CartItemResponse;
import hello.myshop.biz.cartitem.entity.CartItem;
import hello.myshop.biz.cartitem.repository.CartItemJpaRepository;
import hello.myshop.biz.products.entity.Product;
import hello.myshop.biz.products.repository.ProductJpaRepository;
import hello.myshop.biz.user.entity.User;
import hello.myshop.biz.user.repository.jpa.UserJpaRepository;
import hello.myshop.core.response.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemServiceImpl implements CartItemService {

    private final CartItemJpaRepository cartItemJpaRepository;

    private final ProductJpaRepository productJpaRepository;

    private final UserJpaRepository userJpaRepository;

    private final CartItemConvertor cartItemConvertor;

    @Override
    public Long addCartItem(CartItemAdd add) {

        // 불필요한 데이터 로드
//        Product product = productJpaRepository.findById(add.getProductId())
//                .orElseThrow(() -> new CustomException("PROD0001"));
//
//        User user = userJpaRepository.findById(add.getUserId())
//                .orElseThrow(() -> new CustomException("USER0001"));

        if (!productJpaRepository.existsById(add.getProductId())) {
            throw new CustomException("PROD0001");
        }

        if (!userJpaRepository.existsById(add.getUserId())) {
            throw new CustomException("USER0001");
        }

        // Proxy 객체를 사용
        Product product = productJpaRepository.getReferenceById(add.getProductId());
        User user = userJpaRepository.getReferenceById(add.getUserId());

        CartItem cartItem = cartItemConvertor.toEntity(add, user, product);
        CartItem saveCartItem = cartItemJpaRepository.save(cartItem);


        return saveCartItem.getId();
    }

    @Override
    public Void deleteCartItem(Long id, Long userId) {
        if (!cartItemJpaRepository.existsByIdAndUserId(id, userId)) {
            throw new CustomException("CART0001");
        }
        cartItemJpaRepository.deleteById(id);
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public CartItemResponse.MainResponse getCartItems(Long userId) {
        List<CartItem> cartItems = cartItemJpaRepository
                .selectByCartItemListByUserId(userId);

        List<CartItemResponse> cartItemResponseList = cartItems.stream().map(cartItem -> cartItemConvertor.toResponse(cartItem))
                .collect(Collectors.toList());

        return CartItemResponse.MainResponse.builder()
                .cnt(cartItemResponseList.size())
                .cartItems(cartItemResponseList)
                .build();
    }
}
