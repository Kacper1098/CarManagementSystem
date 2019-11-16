package stefanowicz.kacper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import stefanowicz.kacper.model.Car;
import stefanowicz.kacper.model.enums.Color;
import stefanowicz.kacper.service.CarsService;
import stefanowicz.kacper.service.enums.SortBy;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public class SortCarTests {

    private CarsService carsService;

    private Map<SortBy, List<Car>> createExpectedResults(){



        return Map.of(
                SortBy.COLOR, List.of(
                        Car
                                .builder()
                                .model("PORSHE")
                                .mileage(BigDecimal.valueOf(1200))
                                .price(BigDecimal.valueOf(359000))
                                .color(Color.BLACK)
                                .components(
                                        List.of("RADIO", "FIRE EXTINGUISHER")
                                ).build(),
                        Car
                                .builder()
                                .model("AUDI")
                                .mileage(BigDecimal.valueOf(2300))
                                .price(BigDecimal.valueOf(150000))
                                .color(Color.GREEN)
                                .components(
                                        List.of("AIR CONDITIONING", "GPS")
                                ).build(),
                        Car
                                .builder()
                                .model("BMW")
                                .mileage(BigDecimal.valueOf(1500))
                                .price(BigDecimal.valueOf(120000))
                                .color(Color.RED)
                                .components(
                                        List.of("ABS", "ALLOY WHEELS")
                                ).build()
                ),
                SortBy.MILEAGE, List.of(
                        Car
                                .builder()
                                .model("PORSHE")
                                .mileage(BigDecimal.valueOf(1200))
                                .price(BigDecimal.valueOf(359000))
                                .color(Color.BLACK)
                                .components(
                                        List.of("RADIO", "FIRE EXTINGUISHER")
                                ).build(),
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
                                .model("AUDI")
                                .mileage(BigDecimal.valueOf(2300))
                                .price(BigDecimal.valueOf(150000))
                                .color(Color.GREEN)
                                .components(
                                        List.of("AIR CONDITIONING", "GPS")
                                ).build()
                ),
                SortBy.MODEL, List.of(
                        Car
                                .builder()
                                .model("AUDI")
                                .mileage(BigDecimal.valueOf(2300))
                                .price(BigDecimal.valueOf(150000))
                                .color(Color.GREEN)
                                .components(
                                        List.of("AIR CONDITIONING", "GPS")
                                ).build(),
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
                                        List.of("RADIO", "FIRE EXTINGUISHER")
                                ).build()
                ),
                SortBy.PRICE, List.of(
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
                                .model("AUDI")
                                .mileage(BigDecimal.valueOf(2300))
                                .price(BigDecimal.valueOf(150000))
                                .color(Color.GREEN)
                                .components(
                                        List.of("AIR CONDITIONING", "GPS")
                                ).build(),
                        Car
                                .builder()
                                .model("PORSHE")
                                .mileage(BigDecimal.valueOf(1200))
                                .price(BigDecimal.valueOf(359000))
                                .color(Color.BLACK)
                                .components(
                                        List.of("RADIO", "FIRE EXTINGUISHER")
                                ).build()
                )
        );
    }

    @BeforeEach
    public void initCarsService(){
        carsService = new CarsService("resources/cars.json");
    }

    @ParameterizedTest
    @EnumSource(SortBy.class)
    @DisplayName("Sort cars in natural order")
    public void test1(SortBy sortBy){
        Map<SortBy, List<Car>> expectedResults = createExpectedResults();
        Assertions
                .assertEquals(carsService.sortCar(sortBy, false), expectedResults.get(sortBy));
    }


}
