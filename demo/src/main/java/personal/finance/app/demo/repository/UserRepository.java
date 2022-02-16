package personal.finance.app.demo.repository;

import org.springframework.data.repository.CrudRepository;
import personal.finance.app.demo.domain.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
