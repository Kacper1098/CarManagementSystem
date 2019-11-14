package stefanowicz.kacper.service;

import org.eclipse.collections.impl.collector.BigDecimalSummaryStatistics;
import org.eclipse.collections.impl.collector.Collectors2;
import stefanowicz.kacper.model.PriceMileageStatistics;
import stefanowicz.kacper.model.enums.Color;
import stefanowicz.kacper.converter.CarsJsonConverter;
import stefanowicz.kacper.exception.AppException;
import stefanowicz.kacper.model.Car;
import stefanowicz.kacper.service.enums.SortBy;
import stefanowicz.kacper.validators.CarValidator;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarsService {

    private final List<Car> cars;

    public CarsService( String filename ) {
        this.cars = getCarsFromJson( filename );
    }

    private List<Car> getCarsFromJson( String filename ) {

        var carsConverter = new CarsJsonConverter(filename);
        var carValidator = new CarValidator();
        var counter = new AtomicInteger(1);

        return carsConverter
                .fromJson()
                .orElseThrow(() -> new AppException("cars service - json convertion exception"))
                .stream()
                .filter( car -> {

                    var errors = carValidator.validate( car );
                    if ( carValidator.hasErrors() ) {
                        System.out.println("--------------------------------------");
                        System.out.println("-- Validation error for car no. " + counter.get() + " --");
                        System.out.println("--------------------------------------");
                        errors.forEach( ( k, v ) -> System.out.println(k + ": " + v) );
                    }
                    counter.incrementAndGet();
                    return !carValidator.hasErrors();
                } ).collect(Collectors.toList());
    }

    /**
     *
     * @param sortBy
     * @param descendingOrder
     * @return List of cars sorted by given sortBy argument and in order, dependent on descendingOrder argument.
     */
    public List<Car> sortCar(SortBy sortBy, boolean descendingOrder)
    {
        Stream<Car> carStream = switch (sortBy)
                {
                    case COLOR -> this.cars
                            .stream()
                            .sorted(Comparator.comparing((car -> car.getColor().name())));
                    case PRICE -> this.cars
                            .stream()
                            .sorted(Comparator.comparing(Car::getPrice));
                    case MODEL -> this.cars
                            .stream()
                            .sorted(Comparator.comparing(Car::getModel));
                    case MILEAGE -> this.cars
                            .stream()
                            .sorted(Comparator.comparing(Car::getMileage));
                };

        List<Car> sortedCars = carStream.collect(Collectors.toList());

        if(descendingOrder)
            Collections.reverse(sortedCars);

        return sortedCars;
    }

    /**
     *
     * @param mileage
     * @return List of cars with higher mileage than value given as argument.
     */
    public List<Car> getHigherMileage(BigDecimal mileage)
    {
        if( mileage.compareTo(BigDecimal.ZERO) <= 0)
        {
            throw new AppException("Mileage value has to be greater than 0");
        }
        return this.cars
                .stream()
                .filter(car -> car.getMileage().compareTo(mileage) > 0)
                .collect(Collectors.toList());
    }

    /**
     *
     * @return Map of color and quantity of cars with this color, sorted by value in descending order.
     */
    public Map<Color, Long> countByColor()
    {
        return this.cars
                .stream()
                .collect(Collectors.groupingBy(Car::getColor, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
    }

    /**
     *
     * @return Map of model name as key and most expensive car of this model as value, sorted by key in descending order.
     */
    public Map<String, Car> mostExpensiveOfModel()
    {
        return this.cars
                .stream()
                .collect(Collectors.groupingBy(Car::getModel, Collectors.collectingAndThen(
                        Collectors.maxBy(Comparator.comparing(Car::getPrice)),
                        maxPrice -> maxPrice
                                .orElseThrow(() ->new AppException("There is no most expensive car in this model"))
                )))
                .entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey, Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
    }

    /**
     * Method prints out maximum, minimum and average value of price and mileage.
     */
    public PriceMileageStatistics priceMileageStatistics()
    {
        BigDecimalSummaryStatistics priceStatistics =
                cars
                .stream()
                .collect(Collectors2.summarizingBigDecimal(Car::getPrice));
        BigDecimalSummaryStatistics mileageStatistics =
                cars
                .stream()
                .collect(Collectors2.summarizingBigDecimal(Car::getMileage));

        return PriceMileageStatistics
                .builder()
                .averageMileage(mileageStatistics.getAverage())
                .maximumMileage(mileageStatistics.getMax())
                .minimumMileage(mileageStatistics.getMin())
                .averagePrice(priceStatistics.getAverage())
                .maximumPrice(priceStatistics.getMax())
                .minimumPrice(priceStatistics.getMin())
                .build();
    }

    /**
     *
     * @return Most expensive car or list of cars, if there are more than one with the same maximum price.
     */
    public List<Car> getMostExpensive() {
        return cars
                .stream()
                .collect(Collectors.groupingBy(Car::getPrice))
                .entrySet().stream()
                .max(Comparator.comparingDouble(e -> e.getKey().doubleValue()))
                .orElseThrow()
                .getValue();
    }

    /**
     *
     * @return List of cars with sorted components in alphabetical order.
     */
    public List<Car> getCarsSortedComponents(){
        return cars
                .stream()
                .peek(car -> car.setComponents(car.getComponents().stream().sorted().collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    /**
     *
     * @return Map of component name as key and list of cars, that contains this component, as value.
     */
    public Map<String, List<Car>> getCarsByComponents()
    {
        return cars
                .stream()
                .flatMap(car -> car.getComponents().stream())
                .distinct()
                .collect(Collectors.toMap(
                        Function.identity(),
                        component -> cars
                                .stream()
                                .filter(car -> car.getComponents().contains(component))
                                .collect(Collectors.toList())))
                .entrySet().stream()
                .sorted( ( e1, e2 ) -> Integer.compare( e2.getValue().size(), e1.getValue().size() ) )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2 ) -> v1, LinkedHashMap::new));
    }

    /**
     *
     * @param from From price
     * @param to To price
     * @return List of cars in a given price range
     */
    public List<Car> getCarsInPriceRange(BigDecimal from, BigDecimal to) {
        if(from.compareTo(to) > 0)
        {
            throw new AppException("FromPrice has to bo less than or equal to ToPrice");
        }
        return this.cars
                .stream()
                .filter(car -> car.getPrice().compareTo(from) >= 0 && car.getPrice().compareTo(to) <= 0)
                .sorted(Comparator.comparing(Car::getModel))
                .collect(Collectors.toList());
    }
}
