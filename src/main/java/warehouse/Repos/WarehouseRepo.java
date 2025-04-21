package warehouse.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import warehouse.Entities.WarehouseEntity;
import warehouse.Entities.WarehouseMovementEntity;

import java.util.List;

@Repository
public interface WarehouseRepo extends JpaRepository<WarehouseEntity,Long> {
//    List<WarehouseMovementEntity> findByRackIdAndAction(Long rackId, String stored);
}
