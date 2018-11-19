package cr.dbo.ops.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cr.dbo.ops.FLAW;

@Repository
public interface FLAWRepository extends CrudRepository<FLAW, Long>{
		
}
