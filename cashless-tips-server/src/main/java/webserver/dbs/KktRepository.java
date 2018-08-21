package webserver.dbs;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface KktRepository extends CrudRepository<RegisteredKkt, Integer> {
    boolean existsByKkt(String kkt);
    List<RegisteredKkt> getAllByInn(String inn);
    RegisteredKkt getByKkt(String kkt);
}