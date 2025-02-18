package hello.myshop.biz.products.repository;

import hello.myshop.biz.products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    int countByIdIn(List<Long> ids);

    List<Product> getProductsByIdIn(List<Long> ids);
}
