package cr.dbo.ops.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cr.dbo.ops.SECURITYPROJECT;

@Repository
public interface SECURITYPROJECTRepository extends CrudRepository<SECURITYPROJECT, Integer>{
		
}
