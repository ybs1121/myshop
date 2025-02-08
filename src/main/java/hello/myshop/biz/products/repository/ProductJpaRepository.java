package hello.myshop.biz.products.repository;

import hello.myshop.biz.products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

}
