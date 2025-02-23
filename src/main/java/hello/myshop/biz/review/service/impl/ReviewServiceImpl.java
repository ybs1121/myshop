package hello.myshop.biz.review.service.impl;

import hello.myshop.biz.products.hepler.ProductHelper;
import hello.myshop.biz.review.common.mapper.ReviewConverter;
import hello.myshop.biz.review.dto.ReviewRequest;
import hello.myshop.biz.review.entity.Review;
import hello.myshop.biz.review.helper.ReviewHelper;
import hello.myshop.biz.review.repository.ReviewJpaRepository;
import hello.myshop.biz.review.service.ReviewService;
import hello.myshop.biz.user.hepler.UserHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewJpaRepository reviewJpaRepository;
    private final UserHelper userHelper;
    private final ProductHelper productHelper;
    private final ReviewHelper reviewHelper;
    private final ReviewConverter reviewConverter;

    @Override
    public Long addReview(ReviewRequest review) {

        //1. User 검증
        userHelper.validateUserExist(review.getUserId());
        //2. Product 검증
        productHelper.validateProductExist(review.getProductId());

        //3.저장
        Review addReview = reviewConverter.toEntity(review, userHelper.getUserReferenceById(review.getUserId())
                , productHelper.getProductReferenceById(review.getProductId()));

        Review saveReview = reviewJpaRepository.save(addReview);

        return saveReview.getId();
    }

    @Override
    public Long deleteReview(Long id) {
        reviewHelper.validateReviewExist(id);
        reviewJpaRepository.deleteById(id);
        return id;
    }
}
