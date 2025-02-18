package hello.myshop.biz.order.entity;

import hello.myshop.biz.order.common.constant.OrderStatus;
import hello.myshop.biz.user.entity.User;
import hello.myshop.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
public class Orders extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    @Column(precision = 10, scale = 2, nullable = false, name = "total_price")
    private BigDecimal totalPrice;

    @Column(nullable = false, name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
