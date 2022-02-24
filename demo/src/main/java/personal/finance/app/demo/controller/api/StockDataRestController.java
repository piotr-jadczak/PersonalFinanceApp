package personal.finance.app.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import personal.finance.app.demo.domain.entity.saving.StockData;
import personal.finance.app.demo.domain.entity.saving.StockDataBuffer;
import personal.finance.app.demo.service.contract.StockDataService;

@RestController
public class StockDataRestController {

    private final StockDataService stockDataService;

    @Autowired
    public StockDataRestController(StockDataService stockDataService) {
        this.stockDataService = stockDataService;
    }

    @GetMapping("/api/stock/all-buffered")
    public Iterable<StockDataBuffer> getAllBufferedStocksData() {
        return stockDataService.getAllBufferedStocks();
    }

    @GetMapping("/api/stock/all-stock-data")
    public Iterable<StockData> getAllStocksData() { return stockDataService.getAllDataStocks(); }
}
