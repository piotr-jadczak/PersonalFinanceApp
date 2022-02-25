package personal.finance.app.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import personal.finance.app.demo.domain.entity.saving.CurrencyData;
import personal.finance.app.demo.domain.entity.saving.CurrencyDataBuffer;
import personal.finance.app.demo.domain.entity.saving.StockData;
import personal.finance.app.demo.domain.entity.saving.StockDataBuffer;
import personal.finance.app.demo.service.contract.CurrencyDataService;
import personal.finance.app.demo.service.contract.StockDataService;

@RestController
public class MarketDataRestController {

    private final StockDataService stockDataService;
    private final CurrencyDataService currencyDataService;

    @Autowired
    public MarketDataRestController(StockDataService stockDataService,
                                    CurrencyDataService currencyDataService) {
        this.stockDataService = stockDataService;
        this.currencyDataService = currencyDataService;
    }

    @GetMapping("/api/stock/all-buffered")
    public Iterable<StockDataBuffer> getAllBufferedStocksData() {

        return stockDataService.getAllBufferedStocks();
    }

    @GetMapping("/api/stock/all-stock-data")
    public Iterable<StockData> getAllStocksData() {

        return stockDataService.getAllDataStocks();
    }

    @GetMapping("/api/currency/all-buffered")
    public Iterable<CurrencyDataBuffer> getAllBufferedCurrencies() {

        return currencyDataService.getAllBufferedCurrencies();
    }

    @GetMapping("/api/currency/all-currency-data")
    public Iterable<CurrencyData> getAllCurrencyData() {

        return currencyDataService.getAllDataCurrency();
    }
}
