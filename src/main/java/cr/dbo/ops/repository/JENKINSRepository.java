package cr.dbo.ops.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cr.dbo.ops.JENKINS;

@Repository
public interface JENKINSRepository extends CrudRepository<JENKINS, Integer>{
		
}
