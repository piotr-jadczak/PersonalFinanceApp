package personal.finance.app.demo.domain.entity.saving;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
public class CurrencyData {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String currencyName;

    @NotBlank
    @Column(unique = true)
    private String symbol;

    @DecimalMin("0.0")
    private double currentPrice;

    @NotNull
    private LocalTime fetchTime;

    public CurrencyData(String currencyName, String symbol) {
        this.currencyName = currencyName;
        this.symbol = symbol;
        this.currentPrice = 0.0;
        this.fetchTime = LocalTime.of(12, 0);
    }

    public void updateCurrencyData(CurrencyDataBuffer buffer) {
        this.currentPrice = buffer.getCurrentPrice();
        this.fetchTime = buffer.getFetchTime();
    }
}
