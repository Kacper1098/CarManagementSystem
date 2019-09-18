package stefanowicz.kacper.converter;

import stefanowicz.kacper.model.Car;

import java.util.List;

public class CarsJsonConverter extends JsonConverter<List<Car>> {

    public CarsJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
