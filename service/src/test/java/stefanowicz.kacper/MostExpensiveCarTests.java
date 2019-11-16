package stefanowicz.kacper;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stefanowicz.kacper.model.Car;
import stefanowicz.kacper.model.enums.Color;
import stefanowicz.kacper.service.CarsService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MostExpensiveCarTests {

    private CarsService carsService;
    private List<Car> expectedResult;

    @BeforeEach
    public void init(){
        this.carsService = new CarsService("resources/cars.json");
        this.expectedResult = new ArrayList<>();
        this.expectedResult.add(
                Car
                .builder()
                .model("PORSHE")
                .color(Color.BLACK)
                .price(BigDecimal.valueOf(359000))
                .mileage(BigDecimal.valueOf(1200))
                .components(
                        List.of(
                                "RADIO",
                                "FIRE EXTINGUISHER"
                        )
                ).build()
        );
    }

    @Test
    @DisplayName("Find most expensive car")
    public void test1(){
        Assertions
                .assertEquals(carsService.getMostExpensive(), expectedResult);
    }


}
