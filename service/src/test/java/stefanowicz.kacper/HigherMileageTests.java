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

public class HigherMileageTests {

    private CarsService carsService;
    private BigDecimal mileage;

    @BeforeEach
    public void setCarsService(){
        this.carsService = new CarsService("resources/cars.json");
    }

    // TODO co jezeli podasz mileage a takich samochodow nie bedzie

    @Test
    public void throw_exception_when_wrong_mileage(){
         mileage = BigDecimal.valueOf(-2);

        Assertions
                .assertThrows(AppException.class, () -> carsService.getHigherMileage(mileage));

    }

    @Test
    public void filter_cars_with_higher_mileage(){
         mileage = BigDecimal.valueOf(1300);
         Assertions
                 .assertEquals(
                         carsService.getHigherMileage(mileage),
                         List.of(
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
                                 ).build(),
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
                                         ).build()
                         )
                 );
    }
}
