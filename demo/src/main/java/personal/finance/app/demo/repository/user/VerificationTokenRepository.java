package personal.finance.app.demo.repository.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import personal.finance.app.demo.domain.entity.user.VerificationToken;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository
        extends CrudRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);
}
