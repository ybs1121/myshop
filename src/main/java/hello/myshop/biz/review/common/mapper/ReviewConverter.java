package hello.myshop.biz.review.common.mapper;

import hello.myshop.biz.products.common.mapper.ProductConvertor;
import hello.myshop.biz.products.entity.Product;
import hello.myshop.biz.review.dto.ReviewRequest;
import hello.myshop.biz.review.dto.ReviewResponse;
import hello.myshop.biz.review.entity.Review;
import hello.myshop.biz.user.common.mapper.UserConvertor;
import hello.myshop.biz.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewConverter {

    private final UserConvertor userConvertor;
    private final ProductConvertor productConvertor;

    public Review toEntity(ReviewRequest review, User user, Product product) {
        return Review.builder()
                .user(user)
                .product(product)
                .rating(review.getRating())
                .content(review.getContent())
                .build();
    }

    public ReviewResponse toResponse(Review review, User user, Product product) {
        return ReviewResponse.builder()
                .content(review.getContent())
                .rating(review.getRating())
                .user(userConvertor.toResponse(user))
                .product(productConvertor.toResponse(product))
                .build();
    }


}
