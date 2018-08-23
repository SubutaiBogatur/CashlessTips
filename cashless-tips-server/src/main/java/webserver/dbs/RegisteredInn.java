package webserver.dbs;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "registered_inns", indexes = @Index(name = "inn", columnList = "inn"))
public class RegisteredInn {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "inn", nullable = false)
    private String inn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "row_created_time", nullable = false)
    private Date created;

    @Column(name = "preferred_tips", nullable = false)
    private Integer preferredTips;

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public Integer getPreferredTips() {
        return preferredTips;
    }

    public void setPreferredTips(Integer preferredTips) {
        this.preferredTips = preferredTips;

    }
}
