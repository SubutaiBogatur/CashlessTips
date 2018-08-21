package webserver.dbs;

import org.springframework.data.repository.CrudRepository;

public interface InnRepository extends CrudRepository<RegisteredInn, Integer> {
    boolean existsByInn(String inn);
}
