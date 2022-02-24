package personal.finance.app.demo.domain.entity.saving;

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
public class CurrencyData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String currencyName;

    @DecimalMin("0.0")
    private double currentPrice;

    @NotNull
    private LocalTime fetchTime;

    @NotBlank
    private String searchQuote;
}
