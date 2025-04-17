package warehouse.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import warehouse.Entities.RackEntity;

@Repository
public interface RackRepo extends JpaRepository<RackEntity,Long> {
}
