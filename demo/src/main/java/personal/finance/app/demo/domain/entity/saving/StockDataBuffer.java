package personal.finance.app.demo.domain.entity.saving;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Entity
public class StockDataBuffer {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String companyName;

    @NotBlank
    private String symbol;

    @DecimalMin("0.0")
    private double currentPrice;

    @NotNull
    private LocalTime fetchTime;

    @JsonIgnore
    @NotBlank
    private String searchQuote;

    public StockDataBuffer(String companyName, String symbol, String searchQuote) {
        this.companyName = companyName;
        this.symbol = symbol;
        this.searchQuote = searchQuote;
        fetchTime = LocalTime.of(12, 0);
        currentPrice = 0.0;
    }
}
