package webserver.dbs;

import webserver.controllers.exposed_models.ExposedFeedback;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "paid_tips", indexes = {@Index(name = "inn", columnList = "inn")})
public class PaidTips {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "inn", nullable = false)
    private String inn;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "waiter_id", nullable = true)
    private Integer waiterId;

    @Column(name = "rate", nullable = true)
    private Integer rate;

    @Column(name = "comment", nullable = true)
    private String comment;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)

    private Date created;

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    public PaidTips() {
    }

    public PaidTips(String inn, Long amount, Integer waiterId, Integer rate, String comment) {
        this.inn = inn;
        this.amount = amount;
        this.waiterId = waiterId;
        this.rate = rate;
        this.comment = comment;
    }

    public ExposedFeedback convertToExposedFeedback() {
        return new ExposedFeedback(rate, comment);
    }
}
