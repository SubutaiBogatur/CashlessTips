package webserver.dbs;

import webserver.controllers.exposed_models.ExposedRegisteredInn;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "registered_inns", indexes = @Index(name = "inn", columnList = "inn"))
public class RegisteredInn {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "row_created_time", nullable = false)
    private Date created;

    @Column(name = "inn", nullable = false)
    private String inn;

    @Column(name = "preferred_tips", nullable = false)
    private Integer preferredTips;

    @Column(name = "card_number", nullable = true)
    private String cardNumber;

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    public RegisteredInn() {}

    public RegisteredInn(String inn, Integer preferredTips, String cardNumber) {
        this.inn = inn;
        this.preferredTips = preferredTips;
        this.cardNumber = cardNumber;
    }

    public ExposedRegisteredInn convertToExposed() {
        return new ExposedRegisteredInn(inn, preferredTips, cardNumber);
    }

    public Integer getId() {
        return id;
    }
}
