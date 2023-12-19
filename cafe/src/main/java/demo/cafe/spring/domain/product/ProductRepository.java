package demo.cafe.spring.domain.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * select
     * from product
     * where selling_type in ('SELLING', 'HOLD');
     */
    List<Product> findBySellingStatusIn(List<ProductSellingStatus> sellingStatus);
}
