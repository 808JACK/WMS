package warehouse.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import warehouse.Entities.WarehouseMovementEntity;

@Repository
public interface WarehouseMovementRepo extends JpaRepository<WarehouseMovementEntity,Long> {
}
