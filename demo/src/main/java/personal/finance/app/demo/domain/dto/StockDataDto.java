package personal.finance.app.demo.domain.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

public class StockDataDto {

    @NotBlank
    private String companyName;

    @DecimalMin("0.0")
    private double currentPrice;

    @NotNull
    private LocalTime fetchTime;

}
