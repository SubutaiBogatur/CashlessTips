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

    public static boolean validateFn(String fn) {
        boolean allDigits = fn.chars().map(c -> (char) c).allMatch(Character::isDigit);
        if (!allDigits) {
            return false;
        }

        if (fn.length() > 18) {
            return false;
        }

        return true;
    }

    public static boolean validateFd(String fd) {
        boolean allDigits = fd.chars().map(c -> (char) c).allMatch(Character::isDigit);
        if (!allDigits) {
            return false;
        }

        return true;
    }

    public static boolean validateFp(String fp) {
        boolean allDigits = fp.chars().map(c -> (char) c).allMatch(Character::isDigit);
        if (!allDigits) {
            return false;
        }

        return true;
    }

    public static boolean validateN(String n) {
        // we currently know nothing about allowed n, but it is expected, that it is not long
        if (n.length() > 3) {
            return false;
        }

        return true;
    }
}
