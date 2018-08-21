package webserver.dbs;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "registered_kkts", indexes = {@Index(name = "inn", columnList = "inn"), @Index(name = "kkt", columnList = "kkt")})
public class RegisteredKkt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "kkt", nullable = false)
    private String kkt;

    @Column(name = "inn", nullable = false)
    private String inn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "row_created_time", nullable = false)
    private Date created;

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public void setKkt(String kkt) {
        this.kkt = kkt;
    }
}
