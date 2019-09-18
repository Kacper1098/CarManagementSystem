package stefanowicz.kacper.menu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import stefanowicz.kacper.exception.AppException;
import stefanowicz.kacper.model.Car;
import stefanowicz.kacper.model.PriceMileageStatistics;
import stefanowicz.kacper.model.enums.Color;
import stefanowicz.kacper.service.CarsService;
import stefanowicz.kacper.service.UserDataService;
import stefanowicz.kacper.service.enums.SortBy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class MenuService {

    private final CarsService carsService;

    public MenuService(String filename ) {
        this.carsService = new CarsService(filename);
    }

    public void mainMenu() {

        int option;
        do {
            try {

                option = printMenu();

                switch ( option ) {
                    case 1 -> option1();
                    case 2 -> option2();
                    case 3 -> option3();
                    case 4 -> option4();
                    case 5 -> option5();
                    case 6 -> option6();
                    case 7 -> option7();
                    case 8 -> option8();
                    case 9 -> option9();
                    case 0 -> {
                        UserDataService.close();
                        System.out.println("Have a nice day!");
                        return;
                    }
                    default -> System.out.println("No such option!");
                }

            } catch ( AppException e) {
                System.out.println("---------------------------------------");
                System.out.println("------------------ EXCEPTION ----------");
                System.out.println(e.getMessage());
                System.out.println("---------------------------------------");
            }
        } while ( true );

    }

    public int printMenu() {
        System.out.println("1. Sort cars");
        System.out.println("2. Cars with higher mileage than given one");
        System.out.println("3. Quantity of cars grouped by color");
        System.out.println("4. Most expensive car of particular model");
        System.out.println("5. Statistics for price and mileage");
        System.out.println("6. Most expensive car");
        System.out.println("7. Cars with sorted components");
        System.out.println("8. Cars grouped by components");
        System.out.println("9. Cars in given price range");
        System.out.println("0. Exit");
        return UserDataService.getInt("Choose option:");
    }

    private void option1() {

        SortBy sortBy = UserDataService.getSortBy();
        boolean descending = UserDataService.getBoolean("Descending order");
        List<Car> sortedCars = carsService.sortCar(sortBy, descending);
        System.out.println(toJson(sortedCars));
    }

    private void option2() {
        double mileage = UserDataService.getDouble("Enter mileage:");
        List<Car> withHigherMileageCars = carsService.getHigherMileage(mileage);
        System.out.println(toJson(withHigherMileageCars));
    }

    private void option3() {
        Map<Color, Long> colorCountMap = carsService.countByColor();
        System.out.println(toJson(colorCountMap));
    }

    private void option4() {
        Map<String, Car> carsByModel = carsService.mostExpensiveOfModel();
        System.out.println(toJson(carsByModel));
    }

    private void option5() {
        PriceMileageStatistics priceMileageStatistics = carsService.priceMileageStatistics();
        System.out.println(toJson(priceMileageStatistics));
    }

    private void option6() {
        List<Car> mostExpensive = carsService.getMostExpensive();
        System.out.println(toJson(mostExpensive));
    }

    private void option7() {
        List<Car> withSortedComponents = carsService.getCarsSortedComponents();
        System.out.println(toJson(withSortedComponents));
    }

    private void option8() {
        Map<String, List<Car>> groupedByComponents = carsService.getCarsByComponents();
        System.out.println(toJson(groupedByComponents));
    }

    private void option9() {
        BigDecimal fromPrice = new BigDecimal(UserDataService.getDouble("From price:" ));
        BigDecimal toPrice = new BigDecimal(UserDataService.getDouble("To price:"));
        List<Car> carsInPriceRange = carsService.getCarsInPriceRange(fromPrice, toPrice);
        System.out.println(toJson(carsInPriceRange));
    }

    private static <T> String toJson(T t) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson(t);
        } catch (Exception e) {
            throw new AppException("to json conversion in menu service exception");
        }
    }

}
