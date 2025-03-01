package hello.myshop.biz.review.service;

import hello.myshop.biz.review.dto.ReviewModifyRequest;
import hello.myshop.biz.review.dto.ReviewRequest;
import hello.myshop.biz.review.dto.ReviewResponse;

public interface ReviewService {
    Long addReview(ReviewRequest review);

    Long deleteReview(Long id);

    ReviewResponse modReview(ReviewModifyRequest mod);

    ReviewResponse.MainResponse getReviewsByProductId(Long productId);
}
