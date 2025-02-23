package hello.myshop.biz.review;

import hello.myshop.biz.products.dto.ProductRequest;
import hello.myshop.biz.products.entity.Product;
import hello.myshop.biz.products.service.ProductService;
import hello.myshop.biz.review.dto.ReviewRequest;
import hello.myshop.biz.review.entity.Review;
import hello.myshop.biz.review.repository.ReviewJpaRepository;
import hello.myshop.biz.review.service.ReviewService;
import hello.myshop.biz.user.common.constant.Role;
import hello.myshop.biz.user.dto.UserRequest;
import hello.myshop.biz.user.entity.User;
import hello.myshop.biz.user.service.UserService;
import hello.myshop.core.response.CustomException;
import jakarta.annotation.PostConstruct;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class ReviewServiceIntegrationTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewJpaRepository reviewJpaRepository;


    private Long userId;
    private Long productId1;
    private Long productId2;

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;


    @PostConstruct
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
    void 리뷰_저장() {

        //given
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setContent("좋아요");
        reviewRequest.setRating(5);
        reviewRequest.setUserId(userId);
        reviewRequest.setProductId(productId1);

        //when
        Long saveReviewId = reviewService.addReview(reviewRequest);

        //then
        Review review = reviewJpaRepository.findById(saveReviewId).get();
        Assertions.assertThat(review.getId()).isEqualTo(saveReviewId);
        Assertions.assertThat(review.getContent()).isEqualTo(reviewRequest.getContent());
        Assertions.assertThat(review.getRating()).isEqualTo(reviewRequest.getRating());
        User user = review.getUser();
        Product product = review.getProduct();
        Assertions.assertThat(user.getId()).isEqualTo(userId);
        Assertions.assertThat(product.getId()).isEqualTo(productId1);


    }

    @Test
    void 리뷰_삭제_성공() {
        //Given
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setContent("좋아요");
        reviewRequest.setRating(5);
        reviewRequest.setUserId(userId);
        reviewRequest.setProductId(productId1);
        Long saveReviewId = reviewService.addReview(reviewRequest);
        //when
        reviewService.deleteReview(saveReviewId);
    }

    @Test
    void 리뷰_삭제_실패_리뷰존재X() {

        CustomException customException = assertThrows(CustomException.class,
                () -> reviewService.deleteReview(-10L));

        Assertions.assertThat(customException.getCode()).isEqualTo("REVW0001");
    }
}
