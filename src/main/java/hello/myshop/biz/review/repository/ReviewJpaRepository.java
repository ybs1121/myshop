package hello.myshop.biz.review.repository;

import hello.myshop.biz.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {
}
