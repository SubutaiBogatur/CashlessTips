package webserver.utils;

public class Utils {
    public static boolean validateInn(long inn) {
        if (inn <= 0) {
            return false;
        }

        int numberOfSymbols = Long.toString(inn).length();
        if (numberOfSymbols > 18) {
            return false;
        }

        return true;
    }
}
