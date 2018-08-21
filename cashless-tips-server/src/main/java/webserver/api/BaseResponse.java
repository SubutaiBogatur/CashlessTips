package webserver.api;

public class BaseResponse {
    private Integer code;
    private String message;

    BaseResponse() {
        // default constructor for Jackson
    }

    BaseResponse(String message) {
        this.code = TemplateResponse.OTHER_ERROR.code;
        this.message = message;
    }

    BaseResponse(TemplateResponse response) {
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
