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
    private static final String webClassWithCryptoPrice = "pclqee";

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
            updateCurrencyInfo(currency);
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

    private void updateCurrencyInfo(CurrencyDataBuffer currency) {

        double stockPrice = 0;
        try {
            Document doc = Jsoup.connect(googleSearchURL + currency.getSearchQuote()).get();
            Element stockPriceContent;
            if(currency.getSymbol().equals("BTC") ||
                    currency.getSymbol().equals("ETH")) {
                stockPriceContent = doc.getElementsByClass(webClassWithCryptoPrice).get(0);
            }
            else {
                stockPriceContent = doc.getElementsByClass(webClassWithPrice).get(0);
            }
            stockPrice = Double.parseDouble(stockPriceContent.text().
                    replace(',','.').replaceAll(" ","")
                    .replaceAll("&nbsp;", ""));
        }
        catch (IOException e) {
            System.out.println("Error fetching currency " + currency.getCurrencyName());
        }

        int hour = LocalTime.now().getHour();
        int minutes = LocalTime.now().getMinute();
        currency.setCurrentPrice(stockPrice);
        currency.setFetchTime(LocalTime.of(hour, minutes));
    }
}
