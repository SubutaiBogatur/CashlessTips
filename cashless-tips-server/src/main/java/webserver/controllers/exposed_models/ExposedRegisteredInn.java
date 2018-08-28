package webserver.controllers.exposed_models;

public class ExposedRegisteredInn {
    final private String inn;
    final private Integer preferredTips;
    final private String cardNumber;

    public ExposedRegisteredInn(String inn, Integer preferredTips, String cardNumber) {
        this.inn = inn;
        this.preferredTips = preferredTips;
        this.cardNumber = cardNumber;
    }

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
