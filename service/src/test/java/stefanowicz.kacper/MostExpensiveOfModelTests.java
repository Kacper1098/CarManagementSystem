package stefanowicz.kacper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stefanowicz.kacper.model.Car;
import stefanowicz.kacper.model.enums.Color;
import stefanowicz.kacper.service.CarsService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class MostExpensiveOfModelTests {

    private CarsService carsService;

    @BeforeEach
    public void setCarsService(){
        this.carsService = new CarsService("resources/cars.json");
    }

    private Map<String, Car> createExpectedResults(){
        return Map.of(
                "PORSHE",
                Car
                        .builder()
                        .model("PORSHE")
                        .mileage(BigDecimal.valueOf(1200))
                        .price(BigDecimal.valueOf(359000))
                        .color(Color.BLACK)
                        .components(
                                List.of("RADIO", "FIRE EXTINGUISHER")
                        ).build(),
                "BMW",
                Car
                        .builder()
                        .model("BMW")
                        .mileage(BigDecimal.valueOf(1500))
                        .price(BigDecimal.valueOf(120000))
                        .color(Color.RED)
                        .components(
                                List.of("ABS", "ALLOY WHEELS")
                        ).build(),
                "AUDI",
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
    public void find_most_expensive_of_each_model(){
        Assertions
                .assertEquals(carsService.mostExpensiveOfModel(), createExpectedResults());
    }
}
