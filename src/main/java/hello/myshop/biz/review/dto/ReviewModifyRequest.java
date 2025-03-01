package hello.myshop.biz.review.dto;

import lombok.Data;

@Data
public class ReviewModifyRequest {

    private Long userId;

    private Long productId;

    private Long reviewId;

    private Integer rating;

    private String content;

}
