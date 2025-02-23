package hello.myshop.biz.review.common.mapper;

import hello.myshop.biz.products.dto.ProductRequest;
import hello.myshop.biz.products.dto.ProductResponse;
import hello.myshop.biz.products.entity.Product;
import hello.myshop.biz.review.dto.ReviewRequest;
import hello.myshop.biz.review.entity.Review;
import hello.myshop.biz.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ReviewConverter {

    public Review toEntity(ReviewRequest review, User user, Product product) {
        return Review.builder()
                .user(user)
                .product(product)
                .rating(review.getRating())
                .content(review.getContent())
                .build();
    }


}
