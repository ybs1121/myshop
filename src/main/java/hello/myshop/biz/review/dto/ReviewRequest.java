package hello.myshop.biz.review.dto;

import lombok.Data;

@Data
public class ReviewRequest {

    private Long userId;

    private Long productId;

    private Integer rating;

    private String content;


}
