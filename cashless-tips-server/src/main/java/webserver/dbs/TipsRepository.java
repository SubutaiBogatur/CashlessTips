package webserver.dbs;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TipsRepository extends CrudRepository<PaidTips, Integer> {
    List<PaidTips> getAllByInn(String inn);
}
