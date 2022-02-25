package personal.finance.app.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import personal.finance.app.demo.domain.entity.user.Role;
import personal.finance.app.demo.domain.storage.CurrencyDataInputReader;
import personal.finance.app.demo.domain.storage.StockDataInputReader;
import personal.finance.app.demo.repository.user.RoleRepository;
import personal.finance.app.demo.service.contract.CurrencyDataService;
import personal.finance.app.demo.service.contract.StockDataService;

import javax.swing.*;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	@Autowired
	public CommandLineRunner setupApp(RoleRepository roleRepository,
									  StockDataInputReader stockReader,
									  StockDataService stockDataService,
									  CurrencyDataInputReader currencyReader,
									  CurrencyDataService currencyDataService) {
		return args -> {
			roleRepository.save(new Role("user"));
			roleRepository.save(new Role("admin"));
			stockReader.initialSetUp();
			stockDataService.updateStocksBufferPrices();
			stockDataService.copyStockDataFromBuffer();
			Timer timer = new Timer(150000, stockDataService);
			timer.start();
			currencyReader.initialSetUp();
			currencyDataService.updateCurrencyDataBufferPrices();
			currencyDataService.copyCurrencyDataFromBuffer();
			Timer timer2 = new Timer(150000, currencyDataService);
			timer2.start();

		};
	}

}
