package hello.myshop.biz.review.controller;

import hello.myshop.biz.review.dto.ReviewRequest;
import hello.myshop.biz.review.service.ReviewService;
import hello.myshop.core.response.ApiResponse;
import hello.myshop.core.response.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("")
    private ResponseEntity<ApiResponse<Long>> createReview(@RequestBody ReviewRequest review) {
        return ResponseUtil.success(reviewService.addReview(review));
    }

    @DeleteMapping("{id}")
    private ResponseEntity<ApiResponse<Long>> deleteReview(@PathVariable Long id) {
        return ResponseUtil.success(reviewService.deleteReview(id));
    }
}
