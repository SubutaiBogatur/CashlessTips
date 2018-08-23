package webserver.dbs;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FnRepository extends CrudRepository<RegisteredFn, Integer> {
    boolean existsByFn(String fn);
    List<RegisteredFn> getAllByInn(String inn);
    RegisteredFn getByFn(String fn);
}