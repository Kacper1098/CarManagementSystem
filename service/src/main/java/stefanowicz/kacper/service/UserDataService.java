package stefanowicz.kacper.service;

import stefanowicz.kacper.exception.AppException;
import stefanowicz.kacper.service.enums.SortBy;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public final class UserDataService {

    private UserDataService() {}

    private static Scanner sc = new Scanner(System.in);

    public static String getString( String message ) {
        System.out.println(message);
        return sc.nextLine();
    }

    public static int getInt( String message ) {
        System.out.println(message);

        String text = sc.nextLine();
        if ( !text.matches("\\d+") ) {
            throw new AppException("This is not int value!");
        }

        return Integer.parseInt(text);
    }

    public static double getDouble( String message ) {
        System.out.println(message);

        String text = sc.nextLine();
        if ( !text.matches("(\\d+\\.)?\\d+") ) {
            throw new AppException("This is not double value!");
        }

        return Double.parseDouble(text);
    }

    public static boolean getBoolean( String message ) {
        System.out.println(message + " [ y /n? ] ");
        String text = sc.nextLine();
        if( !text.toLowerCase().matches("[yn]")){
            throw new AppException("Invalid value! Permited values are [y, n]");
        }
        return text.toLowerCase().equals("y");
    }

    public static SortBy getSortBy( ) {
        var counter = new AtomicInteger(0);
        Arrays
                .stream(SortBy.values())
                .forEach(s -> System.out.println(counter.incrementAndGet() + ". " + s));
        int choice = getInt("Choose sort type:");

        if ( choice < 1 || choice > SortBy.values().length ) {
            throw new AppException("No sort type with given number!");
        }
        return SortBy.values()[choice - 1];
    }

    public static void close() {
        if ( sc != null ) {
            sc.close();
            sc = null;
        }
    }

}
