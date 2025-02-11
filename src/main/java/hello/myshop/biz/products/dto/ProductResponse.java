package hello.myshop.biz.products.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
public class ProductResponse {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;


    @Builder
    @Data
    public static class MainResponse {
        private List<ProductResponse> products;
        private int cnt;
    }
}
