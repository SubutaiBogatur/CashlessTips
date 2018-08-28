package webserver.dbs;


import webserver.controllers.exposed_models.ExposedWaiter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "registered_waiters", indexes = @Index(name = "inn", columnList = "inn"))
public class RegisteredWaiter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    private Date created;

    @Column(name = "inn", nullable = false)
    private String inn;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "card_number", nullable = true)
    private String cardNumber;

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    public RegisteredWaiter() {}

    public RegisteredWaiter(String inn, String name, String cardNumber) {
        this.inn = inn;
        this.name = name;
        this.cardNumber = cardNumber;
    }

    public ExposedWaiter convertToExposed() {
        return new ExposedWaiter(inn, name, cardNumber, id);
    }

    public Integer getId() {
        return id;
    }
}
