package personal.finance.app.demo.service.contract;

import personal.finance.app.demo.domain.entity.saving.CurrencyData;
import personal.finance.app.demo.domain.entity.saving.CurrencyDataBuffer;

import java.awt.event.ActionListener;

public interface CurrencyDataService extends ActionListener {

    void updateCurrencyDataBufferPrices();
    Iterable<CurrencyDataBuffer> getAllBufferedCurrencies();
    Iterable<CurrencyData> getAllDataCurrency();
    void copyCurrencyDataFromBuffer();
}
