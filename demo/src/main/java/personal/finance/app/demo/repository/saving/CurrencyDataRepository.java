package personal.finance.app.demo.repository.saving;

import org.springframework.data.repository.CrudRepository;
import personal.finance.app.demo.domain.entity.saving.CurrencyData;

public interface CurrencyDataRepository extends CrudRepository<CurrencyData, Long> {
}
