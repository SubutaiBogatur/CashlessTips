package webserver.dbs;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WaiterRepository extends CrudRepository<RegisteredWaiter, Integer> {
    RegisteredWaiter getByInnAndName(String inn, String name);
    boolean existsByInnAndName(String inn, String name);
    List<RegisteredWaiter> getAllByInn(String inn);
}
