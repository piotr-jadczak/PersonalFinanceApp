package personal.finance.app.demo.domain.entity.saving;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Data
@NoArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Min(1)
    @Max(1000000)
    private long quantity;

    @DecimalMin("0.1")
    private double averageBought;

    @ManyToOne
    private StockData data;
}
