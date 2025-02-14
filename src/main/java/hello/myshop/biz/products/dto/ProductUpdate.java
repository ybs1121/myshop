package hello.myshop.biz.products.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdate {

    private Long userId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
}
