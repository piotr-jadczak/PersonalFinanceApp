package personal.finance.app.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockTestDto {

    private String name;
    private double price;
    private LocalTime time;
}
