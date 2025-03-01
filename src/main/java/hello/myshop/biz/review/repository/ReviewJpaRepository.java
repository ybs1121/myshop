package hello.myshop.biz.review.repository;

import hello.myshop.biz.review.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {
    @EntityGraph(attributePaths = {"user", "product"})
    List<Review> findByProductId(Long productId);
}
