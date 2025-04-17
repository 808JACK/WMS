package warehouse.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import warehouse.Entities.WarehouseEntity;

@Repository
public interface WarehouseRepo extends JpaRepository<WarehouseEntity,Long> {
}
