package meme.kiteq.tipit.model;

import java.io.Serializable;

public class ExposedRegisteredInn implements Serializable {
    public String inn;
    public Integer preferredTips;
    public String cardNumber;

    public String getInn() {
        return inn;
    }

    public Integer getPreferredTips() {
        return preferredTips;
    }

    public String getCardNumber() {
        return cardNumber;
    }
}
