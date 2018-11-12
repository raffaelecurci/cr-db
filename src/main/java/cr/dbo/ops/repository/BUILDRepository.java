package cr.dbo.ops.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cr.dbo.ops.BUILD;

@Repository
public interface BUILDRepository extends CrudRepository<BUILD, Integer>{
		
}
