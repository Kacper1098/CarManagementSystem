package stefanowicz.kacper.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriceMileageStatistics {
    private BigDecimal averagePrice;
    private BigDecimal maximumPrice;
    private BigDecimal minimumPrice;
    private BigDecimal averageMileage;
    private BigDecimal maximumMileage;
    private BigDecimal minimumMileage;
}
