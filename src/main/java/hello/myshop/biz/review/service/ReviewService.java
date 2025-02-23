package hello.myshop.biz.review.service;

import hello.myshop.biz.review.dto.ReviewRequest;

public interface ReviewService {
    Long addReview(ReviewRequest review);

    Long deleteReview(Long id);
}
