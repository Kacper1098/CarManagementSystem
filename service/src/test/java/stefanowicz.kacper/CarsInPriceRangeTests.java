package stefanowicz.kacper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

    // TODO brak testu sprawdzajacego czy cena podana jako argument nie jest null
    // TODO brak testu ktory sprawdza ze nie ma elementow kiedy podano prawidlowy przedziale ale poza cenami elementow ktore sa

    @Test
    public void throws_when_from_price_is_greater_than_to_price(){
        fromPrice = BigDecimal.valueOf(250000);
        toPrice = BigDecimal.valueOf(200000);

        Assertions
                .assertThrows(
                        AppException.class,
                        () -> carsService.getCarsInPriceRange(fromPrice, toPrice)
                );
    }

    @Test
    public void return_cars_from_given_range(){
        fromPrice = BigDecimal.valueOf(100000);
        toPrice = BigDecimal.valueOf(150000);

        var result = carsService.getCarsInPriceRange(fromPrice, toPrice);

        Assertions
                .assertEquals(result, expectedResult);
    }
}
