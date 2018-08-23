package webserver.controllers.response_utils;

public class BaseResponse {
    private Integer code;
    private String message;

    BaseResponse() {
        // default constructor for Jackson
    }

    public BaseResponse(String message) {
        this.code = TemplateResponse.OTHER_ERROR.code;
        this.message = message;
    }

    public BaseResponse(TemplateResponse response) {
        this.code = response.code;
        this.message = response.message;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
