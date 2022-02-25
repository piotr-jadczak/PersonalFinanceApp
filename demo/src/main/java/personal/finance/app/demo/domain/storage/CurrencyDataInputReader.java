package personal.finance.app.demo.domain.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import personal.finance.app.demo.domain.entity.saving.CurrencyData;
import personal.finance.app.demo.domain.entity.saving.CurrencyDataBuffer;
import personal.finance.app.demo.repository.saving.CurrencyDataBufferRepository;
import personal.finance.app.demo.repository.saving.CurrencyDataRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CurrencyDataInputReader {

    private static final String FILE_PATH = "static/data_input/currency_input.csv";
    private static final String delimiter = ",";

    private final CurrencyDataBufferRepository bufferRepository;
    private final CurrencyDataRepository currencyRepository;
    private CurrencyDataInput[] dataInput;

    @Autowired
    public CurrencyDataInputReader(CurrencyDataBufferRepository bufferRepository,
                                   CurrencyDataRepository currencyRepository) {
        this.bufferRepository = bufferRepository;
        this.currencyRepository = currencyRepository;
    }

    public void getData() {

        List<CurrencyDataInput> currencyDataList = new ArrayList<>();
        try {
            File file = ResourceUtils.getFile("classpath:" + FILE_PATH);
            if(file.exists() && file.canRead()) {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line;
                String[] rowData;
                while((line = br.readLine()) != null) {
                    rowData = line.split(delimiter);
                    currencyDataList.add(new CurrencyDataInput(rowData[0], rowData[1], rowData[2]));
                }
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("File not found", e);
        }
        catch (IOException e) {
            throw new RuntimeException("Cannot read file", e);
        }
        dataInput = currencyDataList.toArray(new CurrencyDataInput[0]);
    }

    public void loadCurrencyBufferData() {

        List<CurrencyDataBuffer> currencyBuffer = new ArrayList<>();
        for(CurrencyDataInput currency : dataInput) {
            currencyBuffer.add(new CurrencyDataBuffer(currency.getCurrencyName(),
                    currency.getSymbol(), currency.getSearchQuote()));
        }
        bufferRepository.saveAll(currencyBuffer);
    }

    public void loadCurrencyData() {

        List<CurrencyData> currencyData= new ArrayList<>();
        for(CurrencyDataInput currency : dataInput) {
            currencyData.add(new CurrencyData(currency.getCurrencyName(),
                    currency.getSymbol()));
        }
        currencyRepository.saveAll(currencyData);
    }

    public void initialSetUp() {
        getData();
        loadCurrencyBufferData();
        loadCurrencyData();
    }
}
