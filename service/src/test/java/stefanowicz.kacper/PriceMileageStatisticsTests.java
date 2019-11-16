package stefanowicz.kacper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stefanowicz.kacper.model.PriceMileageStatistics;
import stefanowicz.kacper.service.CarsService;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public class PriceMileageStatisticsTests {
    private CarsService carsService;
    private  List<BigDecimal> carPrices;
    private  List<BigDecimal> carMileages;

    @BeforeEach
    public void init(){
        this.carsService = new CarsService("resources/cars.json");
        this.carMileages = List.of(
                BigDecimal.valueOf(1200),
                BigDecimal.valueOf(1500),
                BigDecimal.valueOf(2300)
        );
        this.carPrices = List.of(
                BigDecimal.valueOf(120000),
                BigDecimal.valueOf(150000),
                BigDecimal.valueOf(359000)
        );
    }

    private PriceMileageStatistics createExpectedResult(){
        return PriceMileageStatistics
                .builder()
                .minimumPrice(carPrices.get(0))
                .maximumPrice(carPrices.get(carPrices.size() - 1))
                .averagePrice(
                        carPrices.stream().reduce(BigDecimal.ZERO, BigDecimal::add)
                                .divide(BigDecimal.valueOf(carPrices.size()), MathContext.DECIMAL128)
                )
                .minimumMileage(carMileages.get(0))
                .maximumMileage(carMileages.get(carPrices.size() - 1))
                .averageMileage(
                        carMileages.stream().reduce(BigDecimal.ZERO, BigDecimal::add)
                                .divide(BigDecimal.valueOf(carPrices.size()), MathContext.DECIMAL128)
                )
                .build();
    }

    @Test
    @DisplayName("Calculate price and mileage statistics")
    public void test1(){
        Assertions
                .assertEquals(carsService.priceMileageStatistics(), createExpectedResult());
    }
}
