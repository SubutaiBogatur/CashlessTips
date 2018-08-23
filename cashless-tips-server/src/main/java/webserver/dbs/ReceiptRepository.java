package webserver.dbs;

import org.springframework.data.repository.CrudRepository;

public interface ReceiptRepository extends CrudRepository<ScannedReceipt, Integer> {
    // currently I'm sure that triple (fn, fd, fp) uniquely identifies a receipt
    // but these methods are not used from anywhere, as a result, there is no index for them
    ScannedReceipt getByFnAndFdAndFp(String fn, String fd, String fp);
    boolean existsByFnAndFdAndFp(String fn, String fd, String fp);
}
