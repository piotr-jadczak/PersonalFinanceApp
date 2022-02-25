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

@Entity
@Data
@NoArgsConstructor
public class CurrencyDataBuffer {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String currencyName;

    @NotBlank
    private String symbol;

    @DecimalMin("0.0")
    private double currentPrice;

    @NotNull
    private LocalTime fetchTime;

    @JsonIgnore
    @NotBlank
    private String searchQuote;

    public CurrencyDataBuffer(String currencyName, String symbol, String searchQuote) {
        this.currencyName = currencyName;
        this.symbol = symbol;
        this.searchQuote = searchQuote;
        this.currentPrice = 0.0;
        this.fetchTime = LocalTime.of(12, 0);
    }
}
