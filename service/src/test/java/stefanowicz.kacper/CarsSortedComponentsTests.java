package stefanowicz.kacper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stefanowicz.kacper.model.Car;
import stefanowicz.kacper.model.enums.Color;
import stefanowicz.kacper.service.CarsService;

import java.math.BigDecimal;
import java.util.List;

public class CarsSortedComponentsTests {
    private CarsService carsService;
    private List<Car> expectedResult;

    @BeforeEach
    public void init(){
        this.carsService = new CarsService("resources/cars.json");
        this.expectedResult = createExpectedResult();
    }

    private List<Car> createExpectedResult(){
        return List.of(
                Car
                        .builder()
                        .model("BMW")
                        .mileage(BigDecimal.valueOf(1500))
                        .price(BigDecimal.valueOf(120000))
                        .color(Color.RED)
                        .components(
                                List.of("ABS", "ALLOY WHEELS")
                        ).build(),
                Car
                        .builder()
                        .model("PORSHE")
                        .mileage(BigDecimal.valueOf(1200))
                        .price(BigDecimal.valueOf(359000))
                        .color(Color.BLACK)
                        .components(
                                List.of("FIRE EXTINGUISHER", "RADIO")
                        ).build(),
                Car
                        .builder()
                        .model("AUDI")
                        .mileage(BigDecimal.valueOf(2300))
                        .price(BigDecimal.valueOf(150000))
                        .color(Color.GREEN)
                        .components(
                                List.of("AIR CONDITIONING", "GPS")
                        ).build()
        );
    }

    @Test
    @DisplayName("Return cars with components sorted in natural order")
    public void test1(){
        Assertions
                .assertEquals(carsService.getCarsSortedComponents(), this.expectedResult);
    }
}
