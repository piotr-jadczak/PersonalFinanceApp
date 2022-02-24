package personal.finance.app.demo.domain.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import personal.finance.app.demo.domain.entity.saving.StockData;
import personal.finance.app.demo.domain.entity.saving.StockDataBuffer;
import personal.finance.app.demo.repository.saving.StockDataBufferRepository;
import personal.finance.app.demo.repository.saving.StockDataRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class StockDataInputReader {

    private static final String FILE_PATH = "static/data_input/stock_input.csv";
    private static final String delimiter = ",";
    private final StockDataBufferRepository bufferRepository;
    private final StockDataRepository stockRepository;
    private StockDataInput[] dataInput;

    @Autowired
    public StockDataInputReader(StockDataBufferRepository bufferRepository,
                                StockDataRepository stockRepository) {
        this.bufferRepository = bufferRepository;
        this.stockRepository = stockRepository;
    }

    public void getData() {

        List<StockDataInput> stockDataList = new ArrayList<>();
        try {
            File file = ResourceUtils.getFile("classpath:" + FILE_PATH);
            if(file.exists() && file.canRead()) {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line;
                String[] rowData;
                while((line = br.readLine()) != null) {
                    rowData = line.split(delimiter);
                    stockDataList.add(new StockDataInput(rowData[0], rowData[1], rowData[2]));
                }
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("File not found", e);
        }
        catch (IOException e) {
            throw new RuntimeException("Cannot read file", e);
        }
        dataInput = stockDataList.toArray(new StockDataInput[0]);
    }

    public void loadStockBufferData() {

        List<StockDataBuffer> stocksBuffer = new ArrayList<>();
        for(StockDataInput stock : dataInput) {
            stocksBuffer.add(new StockDataBuffer(stock.getCompanyName(),
                    stock.getSymbol(), stock.getSearchQuote()));
        }
        bufferRepository.saveAll(stocksBuffer);
    }

    public void loadStockData() {

        List<StockData> stocksData = new ArrayList<>();
        for(StockDataInput stock : dataInput) {
            stocksData.add(new StockData(stock.getCompanyName(),
                    stock.getSymbol()));
        }
        stockRepository.saveAll(stocksData);
    }

    public void initialSetUp() {
        getData();
        loadStockBufferData();
        loadStockData();
    }

}
