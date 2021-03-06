package personal.finance.app.demo.repository.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import personal.finance.app.demo.domain.entity.user.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
