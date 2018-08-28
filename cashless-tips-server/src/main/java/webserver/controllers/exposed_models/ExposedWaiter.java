package webserver.controllers.exposed_models;

public class ExposedWaiter {
    private final Integer id;
    private final String inn;
    private final String name;
    private final String cardNumber;

    public ExposedWaiter(String inn, String name, String cardNumber, Integer id) {
        this.inn = inn;
        this.name = name;
        this.cardNumber = cardNumber;
        this.id = id;
    }

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
