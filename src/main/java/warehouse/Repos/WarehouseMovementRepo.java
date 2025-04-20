package warehouse.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import warehouse.Entities.ACTION;
import warehouse.Entities.WarehouseMovementEntity;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface WarehouseMovementRepo extends JpaRepository<WarehouseMovementEntity,Long> {
    Optional<WarehouseMovementEntity> findByProdIdAndAction(Long productId, ACTION action);

    @Query("SELECT COUNT(m) FROM WarehouseMovementEntity m " +
            "WHERE CAST(m.action AS integer) = :action AND m.timeOfMovement >= :startOfDay AND m.action IS NOT NULL")
    long countByActionAndTimeOfMovementAfter(int action, LocalDateTime startOfDay);
//    Optional<WarehouseMovementEntity> findByProdIdAndAction(Long productId, ACTION action);
//
//    @Query("SELECT m.rack.rackId, COUNT(m) as retrievalCount " +
//            "FROM WarehouseMovementEntity m " +
//            "WHERE m.action = 'RETRIEVED' " +
//            "GROUP BY m.rack.rackId " +
//            "ORDER BY COUNT(m) DESC")
//    Optional<Object[]> findTopRackByRetrievalCount();
}
