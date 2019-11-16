package stefanowicz.kacper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stefanowicz.kacper.exception.AppException;
import stefanowicz.kacper.model.Car;
import stefanowicz.kacper.model.enums.Color;
import stefanowicz.kacper.service.CarsService;

import java.math.BigDecimal;
import java.util.List;

public class CarsInPriceRangeTests {

    private CarsService carsService;
    private BigDecimal fromPrice;
    private BigDecimal toPrice;
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
                        .model("AUDI")
                        .price(BigDecimal.valueOf(150000))
                        .color(Color.GREEN)
                        .mileage(BigDecimal.valueOf(2300))
                        .components(
                                List.of(
                                        "AIR CONDITIONING",
                                        "GPS"
                                )
                        ).build(),
                Car
                        .builder()
                        .model("BMW")
                        .price(BigDecimal.valueOf(120000))
                        .color(Color.RED)
                        .mileage(BigDecimal.valueOf(1500))
                        .components(
                                List.of(
                                        "ABS",
                                        "ALLOY WHEELS"
                                )
                        ).build()
        );
    }

    @Test
    @DisplayName("Throws when from price is greater than to price")
    public void test1(){
        fromPrice = BigDecimal.valueOf(250000);
        toPrice = BigDecimal.valueOf(200000);

        Assertions
                .assertThrows(
                        AppException.class,
                        () -> carsService.getCarsInPriceRange(fromPrice, toPrice)
                );
    }

    @Test
    @DisplayName("Return cars from given range")
    public void test2(){
        fromPrice = BigDecimal.valueOf(100000);
        toPrice = BigDecimal.valueOf(150000);

        var result = carsService.getCarsInPriceRange(fromPrice, toPrice);

        Assertions
                .assertEquals(result, expectedResult);
    }

    @Test
    @DisplayName("Throws when one of argument is null")
    public void test3(){
        fromPrice = null;

        Assertions
                .assertThrows(
                        AppException.class,
                        () -> carsService.getCarsInPriceRange(fromPrice, toPrice)
                );
    }

    @Test
    @DisplayName("Return empty list when could not find any cars in given price range")
    public void test4(){
        fromPrice = BigDecimal.valueOf(90000);
        toPrice = BigDecimal.valueOf(100000);

        var result = carsService.getCarsInPriceRange(fromPrice, toPrice);

        Assertions
                .assertTrue(result.isEmpty());
    }
}
