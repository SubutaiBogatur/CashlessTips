package meme.kiteq.tipit.model;

import java.io.Serializable;

public class ExposedWaiter implements Serializable {
    public Integer id;
    public String inn;
    public String name;
    public String cardNumber;

    public String getInn() {
        return inn;
    }

    public String getName() {
        return name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Integer getId() {
        return id;
    }
}
