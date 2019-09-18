package stefanowicz.kacper.main;

import stefanowicz.kacper.menu.MenuService;
import stefanowicz.kacper.service.UserDataService;

public class App {
    public static void main( String[] args ) {
        final String JSON_FILENAME = "cars.json";
        var menuService = new MenuService(JSON_FILENAME);
        menuService.mainMenu();
    }
}
