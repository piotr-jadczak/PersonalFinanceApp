package personal.finance.app.demo.service.imp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import personal.finance.app.demo.domain.entity.saving.CurrencyData;
import personal.finance.app.demo.domain.entity.saving.CurrencyDataBuffer;
import personal.finance.app.demo.domain.storage.MarketDataUpdateStatus;
import personal.finance.app.demo.repository.saving.CurrencyDataBufferRepository;
import personal.finance.app.demo.repository.saving.CurrencyDataRepository;
import personal.finance.app.demo.service.contract.CurrencyDataService;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyDataServiceImp implements CurrencyDataService {

    private final CurrencyDataBufferRepository bufferRepository;
    private final CurrencyDataRepository dataRepository;

    private static final String googleSearchURL = "https://www.google.com/search?q=";
    private static final String webClassWithPrice = "DFlfde SwHCTb";

    public MarketDataUpdateStatus updateStatus;

    @Autowired
    public CurrencyDataServiceImp(CurrencyDataBufferRepository bufferRepository,
                                  CurrencyDataRepository dataRepository) {
        this.bufferRepository = bufferRepository;
        this.dataRepository = dataRepository;
        this.updateStatus = new MarketDataUpdateStatus();
    }

    @Override
    public void updateCurrencyDataBufferPrices() {

        Iterable<CurrencyDataBuffer> bufferCurrencies = bufferRepository.findAll();
        for(CurrencyDataBuffer currency : bufferCurrencies) {
            try {
                updateCurrencyInfo(currency);
            }
            catch (IOException e) {
                throw new RuntimeException("Cannot search stock", e);
            }
        }
        bufferRepository.saveAll(bufferCurrencies);
    }

    @Override
    public Iterable<CurrencyDataBuffer> getAllBufferedCurrencies() {
        return bufferRepository.findAll();
    }

    @Override
    public Iterable<CurrencyData> getAllDataCurrency() {
        return dataRepository.findAll();
    }

    @Override
    public void copyCurrencyDataFromBuffer() {
        Iterable<CurrencyDataBuffer> currencyBuffer = getAllBufferedCurrencies();
        Map<String, CurrencyDataBuffer> bufferMap = new HashMap<>();
        for(CurrencyDataBuffer buffer : currencyBuffer) {
            bufferMap.put(buffer.getSymbol(), buffer);
        }
        Iterable<CurrencyData> currencyData = getAllDataCurrency();
        for(CurrencyData currency : currencyData) {
            currency.updateCurrencyData(bufferMap.get(currency.getSymbol()));
        }
        dataRepository.saveAll(currencyData);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateCurrencyDataBufferPrices();
        if(!updateStatus.isUpdated()) {
            updateStatus.startUpdate();
            copyCurrencyDataFromBuffer();
            updateStatus.endUpdate();
        }
    }

    private void updateCurrencyInfo(CurrencyDataBuffer currency) throws IOException {
        Document doc = Jsoup.connect(googleSearchURL + currency.getSearchQuote()).get();
        Element stockPriceContent = doc.getElementsByClass(webClassWithPrice).get(0);
        double stockPrice = Double.parseDouble(stockPriceContent.text().
                replace(',','.').replaceAll(" ",""));
        int hour = LocalTime.now().getHour();
        int minutes = LocalTime.now().getMinute();
        currency.setCurrentPrice(stockPrice);
        currency.setFetchTime(LocalTime.of(hour, minutes));
    }
}
