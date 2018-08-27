package webserver.dbs;

import webserver.controllers.exposed_models.ExposedRegisteredFn;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "registered_fns", indexes = {@Index(name = "inn", columnList = "inn"), @Index(name = "fn", columnList = "fn")})
public class RegisteredFn {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "fn", nullable = false)
    private String fn;

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

    public void setFn(String fn) {
        this.fn = fn;
    }

    // getters are used for returning class as json:
    public Integer getId() {
        return id;
    }

    public String getFn() {
        return fn;
    }

    public String getInn() {
        return inn;
    }

    public Date getCreated() {
        return this.created;
    }

    // should be used when exposing data to public for safety
    public ExposedRegisteredFn convertToExposed() {
        return new ExposedRegisteredFn(inn, fn);
    }
}
