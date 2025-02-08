package hello.myshop.biz.products.dto;

import hello.myshop.core.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private String userId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;

}
