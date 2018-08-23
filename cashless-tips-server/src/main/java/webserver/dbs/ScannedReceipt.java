package webserver.dbs;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "scanned_receipts", indexes = {@Index(name = "fn", columnList = "fn")})
public class ScannedReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "row_created_time", nullable = false)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "receipt_created_time", nullable = false)
    private Date receiptPaymentDate;

    @Column(name = "sum", nullable = false)
    private Long sum; // в грошах = тысячных рубля

    @Column(name = "fn", nullable = false)
    private String fn; // номер фискального накопителя -- идентифицирует чип в ккт. Подробно про фискальный накопитель смотри: http://spb-kassa.ru/FAQ/%D1%87%D1%82%D0%BE_%D1%82%D0%B0%D0%BA%D0%BE%D0%B5_%D1%84%D0%B8%D1%81%D0%BA%D0%B0%D0%BB%D1%8C%D0%BD%D1%8B%D0%B9_%D0%BD%D0%B0%D0%BA%D0%BE%D0%BF%D0%B8%D1%82%D0%B5%D0%BB%D1%8C.html

    @Column(name = "fd", nullable = false)
    private String fd; // порядковый номер фискального документа (выпущенного этим ккт)

    @Column(name = "fp", nullable = false)
    private String fp; // фискальный признак документа -- фактически, хеш транзакции, считается фискальным накопителем

    @Column(name = "n", nullable = false)
    private String n; // признак расчета

    public ScannedReceipt(Date receiptPaymentDate, Long sum, String fn, String fd, String fp, String n) {
        this.receiptPaymentDate = receiptPaymentDate;
        this.sum = sum;
        this.fn = fn;
        this.fd = fd;
        this.fp = fp;
        this.n = n;
    }

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }
}
