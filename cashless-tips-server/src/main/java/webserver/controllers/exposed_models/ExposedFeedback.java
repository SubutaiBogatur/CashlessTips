package webserver.controllers.exposed_models;

public class ExposedFeedback {
    private final Integer rate;
    private final String comment;

    public ExposedFeedback(Integer rate, String comment) {
        this.rate = rate;
        this.comment = comment;
    }

    public Integer getRate() {
        return rate;
    }

    public String getComment() {
        return comment;
    }
}
