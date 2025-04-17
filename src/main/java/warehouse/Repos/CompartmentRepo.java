package warehouse.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import warehouse.Entities.CompartmentEntity;

@Repository
public interface CompartmentRepo extends JpaRepository<CompartmentEntity,Long> {
}
