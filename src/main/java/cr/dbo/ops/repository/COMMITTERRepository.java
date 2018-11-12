package cr.dbo.ops.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cr.dbo.ops.COMMITTER;

@Repository
public interface COMMITTERRepository extends CrudRepository<COMMITTER,Integer>{

}
