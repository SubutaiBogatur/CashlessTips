package webserver.controllers.response_utils;

public class TipsServerException extends Exception {
    public TipsServerException(String msg) {
        super(msg);
    }

    public static TipsServerException invalidArguments() {
        return new TipsServerException("Invalid arguments passed to method");
    }
}
