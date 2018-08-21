package webserver.utils;

public class Utils {
    public static boolean validateInn(String inn) {
        boolean allDigits = inn.chars().map(c -> (char) c).allMatch(Character::isDigit);
        if (!allDigits) {
            return false;
        }

        if (inn.length() > 18) {
            return false;
        }

        return true;
    }

    public static boolean validateKkt(String kkt) {
        boolean allDigits = kkt.chars().map(c -> (char) c).allMatch(Character::isDigit);
        if (!allDigits) {
            return false;
        }

        if (kkt.length() > 18) {
            return false;
        }

        return true;
    }
}
