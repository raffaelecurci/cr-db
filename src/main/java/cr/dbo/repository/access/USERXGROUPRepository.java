package cr.dbo.repository.access;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cr.dbo.access.USERXGROUP;

@Repository
public interface USERXGROUPRepository extends CrudRepository<USERXGROUP, Long> {

}
