package personal.finance.app.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import personal.finance.app.demo.domain.entity.VerificationToken;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository
        extends CrudRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);
}
