package personal.finance.app.demo.repository.saving;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import personal.finance.app.demo.domain.entity.saving.StockData;

@Repository
public interface StockDataRepository extends CrudRepository<StockData, Long> {
}
