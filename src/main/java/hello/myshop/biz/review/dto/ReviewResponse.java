package hello.myshop.biz.review.dto;

import hello.myshop.biz.products.dto.ProductResponse;
import hello.myshop.biz.user.dto.UserResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ReviewResponse {
    private String content;

    private Integer rating;

    private ProductResponse product;

    private UserResponse user;


    @Data
    @Builder
    public static class MainResponse {
        private int cnt;
        private List<ReviewResponse> reviews;
    }
}
