package cr.dbo.ops.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cr.dbo.ops.COMMIT;

@Repository
public interface COMMITRepository extends CrudRepository<COMMIT, Integer>{

}
