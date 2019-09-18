package stefanowicz.kacper.validators;

import stefanowicz.kacper.validators.generic.AbstractValidator;
import stefanowicz.kacper.model.Car;

import java.math.BigDecimal;
import java.util.Map;

public class CarValidator extends AbstractValidator<Car> {

    @Override
    public Map<String, String> validate(Car car) {

        errors.clear();

        if ( car == null ) {
            errors.put("object", "car object is null");
            return errors;
        }

        if ( !isCarModelValid(car) ) {
            errors.put("model", "car model is not valid, it has to consists of capital letters and whitespaces only");
        }

        if ( !areCarComponentsValid( car ) ) {
            errors.put("components", "car components are not valid, they have to consists of capital letters and whitespaces only");
        }

        if( !isCarPriceValid( car )) {
            errors.put("price", "car price is not valid, it  has to be greater than 0");
        }

        if( !isCarMileageValid( car )) {
            errors.put("mileage", "car mileage is not valid, it has to be greater than 0");
        }

        if( !isCarColorValid( car )) {
            errors.put("color", "car color is not valid,  it cannot be null");
        }

        return errors;

    }

    private boolean isCarModelValid( Car car ) {
        return car.getModel() != null && car.getModel().matches("([A-Z]+\\s)?[A-Z]+");
    }

    private boolean areCarComponentsValid( Car car ) {
        return car.getComponents()
                .stream()
                .allMatch(component -> component.matches("([A-Z]+\\s)?[A-Z]+"));
    }

    private boolean isCarPriceValid( Car car ) {
        return car.getPrice() != null && car.getPrice().compareTo(BigDecimal.ZERO) > 0;
    }

    private boolean isCarMileageValid( Car car ) {
        return car.getMileage() != null && car.getMileage().compareTo(BigDecimal.ZERO) > 0;
    }

    private  boolean isCarColorValid( Car car ) {
        return car.getColor() != null;
    }

}
