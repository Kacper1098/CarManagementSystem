package stefanowicz.kacper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stefanowicz.kacper.model.enums.Color;
import stefanowicz.kacper.service.CarsService;

import java.util.Map;

public class CountByColorTests {

    private CarsService carsService;

    @BeforeEach
    public void setCarsService(){
        this.carsService = new CarsService("resources/cars.json");
    }

    private Map<Color, Long> createExpectedResults(){
        return Map.of(
                Color.RED,1L,
                Color.GREEN,1L,
                Color.BLACK,1L
        );
    }

    @Test
    public void count_colors_and_sort_them(){
        Assertions
                .assertEquals(carsService.countByColor(), createExpectedResults());
    }
}
