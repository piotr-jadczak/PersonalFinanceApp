package personal.finance.app.demo.repository;

import org.springframework.data.repository.CrudRepository;
import personal.finance.app.demo.domain.entity.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
