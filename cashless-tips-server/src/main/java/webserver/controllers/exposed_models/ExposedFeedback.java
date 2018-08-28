package webserver.controllers.exposed_models;

public class ExposedFeedback {
    private final Integer rate;
    private final String comment;
    private final Integer waiterId;

    public ExposedFeedback(Integer rate, String comment, Integer waiterId) {
        this.rate = rate;
        this.comment = comment;
        this.waiterId = waiterId;
    }

    public Integer getRate() {
        return rate;
    }

    public String getComment() {
        return comment;
    }

    public Integer getWaiterId() { return waiterId; }
}
