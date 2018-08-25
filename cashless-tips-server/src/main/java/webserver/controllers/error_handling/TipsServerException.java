package webserver.controllers.error_handling;

public class TipsServerException extends Exception {
    public TipsServerException(String msg) {
        super(msg);
    }

    public static TipsServerException invalidArguments() {
        return new TipsServerException("Invalid arguments passed to method");
    }
}
