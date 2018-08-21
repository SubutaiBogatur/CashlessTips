package webserver.dbs;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class ScannedReceipt {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "row_created_time", nullable = false)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "receipt_created_time", nullable = false)
    private Date receiptPaymentData;

    private Integer sum; // in integer rubles

    private String fn; // номер фискального накопителя -- идентифицирует чип в ккт

    private String fd; // фискальный признак документа -- фактически, хеш транзакции

    private String i; // номер фискального документа (выпущенного этим ккт)
}
