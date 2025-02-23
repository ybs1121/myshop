package hello.myshop.biz.review.helper;

import hello.myshop.biz.review.repository.ReviewJpaRepository;
import hello.myshop.core.response.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewHelper {

    private final ReviewJpaRepository reviewJpaRepository;

    public void validateReviewExist(Long reviewId) {
        if (!reviewJpaRepository.existsById(reviewId)) {
            throw new CustomException("REVW0001");
        }
    }
}
