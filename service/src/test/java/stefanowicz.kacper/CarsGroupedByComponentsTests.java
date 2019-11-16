package stefanowicz.kacper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import stefanowicz.kacper.model.Car;
import stefanowicz.kacper.model.enums.Color;
import stefanowicz.kacper.service.CarsService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class CarsGroupedByComponentsTests {

    private CarsService carsService;
    private Map<String, List<Car>> expectedResult;

    @BeforeEach
    public void init(){
        this.carsService = new CarsService("resources/cars.json");
        this.expectedResult = createExpectedResult();
    }

    private static Map<String, List<Car>> createExpectedResult(){
        return Map.of(
                "ABS",
                List.of(
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
                "ALLOY WHEELS",
                List.of(
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
                "FIRE EXTINGUISHER",
                List.of(
                        Car
                        .builder()
                        .model("PORSHE")
                        .mileage(BigDecimal.valueOf(1200))
                        .price(BigDecimal.valueOf(359000))
                        .color(Color.BLACK)
                        .components(
                                List.of("RADIO", "FIRE EXTINGUISHER")
                        ).build()),
                "RADIO",
                List.of(
                        Car
                        .builder()
                        .model("PORSHE")
                        .mileage(BigDecimal.valueOf(1200))
                        .price(BigDecimal.valueOf(359000))
                        .color(Color.BLACK)
                        .components(
                                List.of("RADIO","FIRE EXTINGUISHER")
                        ).build()
                ),
                "AIR CONDITIONING",
                List.of(
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
                "GPS",
                List.of(
                        Car
                                .builder()
                                .model("AUDI")
                                .mileage(BigDecimal.valueOf(2300))
                                .price(BigDecimal.valueOf(150000))
                                .color(Color.GREEN)
                                .components(
                                        List.of("AIR CONDITIONING", "GPS")
                                ).build()
                )
        );
    }

    private static Stream<String> getMapKeys(){
        return createExpectedResult().keySet().stream();
    }

    @ParameterizedTest
    @MethodSource("getMapKeys")
    @DisplayName("Return cars grouped by components")
    public void test1(String key){
        var result = carsService.getCarsByComponents();
        Assertions
                .assertEquals(result.get(key), expectedResult.get(key));
    }
}
