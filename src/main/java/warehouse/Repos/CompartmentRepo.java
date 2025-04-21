package warehouse.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import warehouse.Entities.CompartmentEntity;

import java.util.List;

@Repository
public interface CompartmentRepo extends JpaRepository<CompartmentEntity,Long> {
//    List<CompartmentEntity> findByRackId(Long rackId);
}
