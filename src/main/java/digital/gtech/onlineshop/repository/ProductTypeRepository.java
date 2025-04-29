package digital.gtech.onlineshop.repository;

import digital.gtech.onlineshop.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, String> {
}
