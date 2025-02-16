package hello.myshop.biz.cartitem.repository;

import hello.myshop.biz.cartitem.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemJpaRepository extends JpaRepository<CartItem, Long> {

    boolean existsByIdAndUserId(Long cartId, Long userId);


    @Query("select distinct c from CartItem c join fetch c.product p join c.user u where u.id = :userId")
    List<CartItem> selectByCartItemListByUserId(@Param("userId") Long userId);

}
