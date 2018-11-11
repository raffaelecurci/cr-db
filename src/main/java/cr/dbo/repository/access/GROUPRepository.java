package cr.dbo.repository.access;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cr.dbo.access.GROUP;
import cr.dbo.access.USER;

@Repository
public interface GROUPRepository extends CrudRepository<GROUP, Long> {
//    public List<USER> findByUsername(String username);
}
