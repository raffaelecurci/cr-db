package cr.dbo.ops.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cr.dbo.ops.PROJECT;

@Repository
public interface PROJECTRepository extends CrudRepository<PROJECT, Integer>{

}
