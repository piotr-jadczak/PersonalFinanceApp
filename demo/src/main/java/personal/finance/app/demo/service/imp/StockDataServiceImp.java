package personal.finance.app.demo.service.imp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import personal.finance.app.demo.domain.entity.saving.StockData;
import personal.finance.app.demo.domain.entity.saving.StockDataBuffer;
import personal.finance.app.demo.domain.storage.StockUpdateStatus;
import personal.finance.app.demo.repository.saving.StockDataBufferRepository;
import personal.finance.app.demo.repository.saving.StockDataRepository;
import personal.finance.app.demo.service.contract.StockDataService;

import javax.transaction.Transactional;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class StockDataServiceImp implements StockDataService {

    private final StockDataBufferRepository bufferRepository;
    private final StockDataRepository stockRepository;

    private static final String googleSearchURL = "https://www.google.com/search?q=";
    private static final String webClassWithPrice = "IsqQVc NprOob wT3VGc";

    public StockUpdateStatus updateStatus;

    @Autowired
    public StockDataServiceImp(StockDataBufferRepository bufferRepository,
                               StockDataRepository stockRepository) {
        this.bufferRepository = bufferRepository;
        this.stockRepository = stockRepository;
        updateStatus = new StockUpdateStatus();
    }

    @Override
    public void updateStocksBufferPrices() {

        Iterable<StockDataBuffer> bufferStocks = bufferRepository.findAll();
        for(StockDataBuffer stock : bufferStocks) {
            try {
                updateStockInfo(stock);
            }
            catch (IOException e) {
                throw new RuntimeException("Cannot search stock", e);
            }
        }
        bufferRepository.saveAll(bufferStocks);
    }

    @Override
    public Iterable<StockDataBuffer> getAllBufferedStocks() {
        return bufferRepository.findAll();
    }

    @Override
    public Iterable<StockData> getAllDataStocks() {
        return stockRepository.findAll();
    }

    @Override
    public void copyStockDataFromBuffer() {
        Iterable<StockDataBuffer> stockBuffer = getAllBufferedStocks();
        Map<String, StockDataBuffer> bufferMap = new HashMap<>();
        for(StockDataBuffer buffer : stockBuffer) {
            bufferMap.put(buffer.getSymbol(), buffer);
        }
        Iterable<StockData> stockData = getAllDataStocks();
        for(StockData stock : stockData) {
            stock.updateStockData(bufferMap.get(stock.getSymbol()));
        }
        stockRepository.saveAll(stockData);
    }

    private void updateStockInfo(StockDataBuffer stock) throws IOException {
        Document doc = Jsoup.connect(googleSearchURL + stock.getSearchQuote()).get();
        Element stockPriceContent = doc.getElementsByClass(webClassWithPrice).get(0);
        double stockPrice = Double.parseDouble(stockPriceContent.text().
                replace(',','.').replaceAll(" ",""));
        int hour = LocalTime.now().getHour();
        int minutes = LocalTime.now().getMinute();
        stock.setCurrentPrice(stockPrice);
        stock.setFetchTime(LocalTime.of(hour, minutes));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateStocksBufferPrices();
        if(!updateStatus.isUpdated()) {
            updateStatus.startUpdate();
            copyStockDataFromBuffer();
            updateStatus.endUpdate();
        }
    }
}
