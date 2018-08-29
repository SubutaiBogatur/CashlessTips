package meme.kiteq.tipit.model;

import java.io.Serializable;

public class ExposedFeedback implements Serializable {
    public Integer rate;
    public String comment;
    public Integer waiterId;

    public Integer getRate() {
        return rate;
    }

    public String getComment() {
        return comment;
    }

    public Integer getWaiterId() { return waiterId; }
}
