package webserver.controllers.response_utils;

/**
 * Class not for json, just for nice templates
 */
public class TemplateResponse {
    int code;
    String message;

    TemplateResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static final TemplateResponse SUCCESS = new TemplateResponse(0, "Success");
    public static final TemplateResponse INTERNAL_ERROR = new TemplateResponse(1, "Unknown internal server error has occurred");
    public static final TemplateResponse INVALID_ARGUMENTS = new TemplateResponse(2, "Invalid arguments");
    public static final TemplateResponse ALREADY_PRESENT = new TemplateResponse(3, "Data already added to server");

    public static final TemplateResponse OTHER_ERROR = new TemplateResponse(100, "Other error occurred");
}
