package warehouse.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import warehouse.Entities.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long userId);

    User getUserByEmail(String email);
}
