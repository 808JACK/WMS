package warehouse.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import warehouse.Entities.ProductEntity;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity,Long> {
}
