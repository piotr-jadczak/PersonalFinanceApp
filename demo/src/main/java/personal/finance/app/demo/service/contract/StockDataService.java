package personal.finance.app.demo.service.contract;

import personal.finance.app.demo.domain.entity.saving.StockData;
import personal.finance.app.demo.domain.entity.saving.StockDataBuffer;

import java.awt.event.ActionListener;

public interface StockDataService extends ActionListener {

    void updateStocksBufferPrices();
    Iterable<StockDataBuffer> getAllBufferedStocks();
    Iterable<StockData> getAllDataStocks();
    void copyStockDataFromBuffer();
}
