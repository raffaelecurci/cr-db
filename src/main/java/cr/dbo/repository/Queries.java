package cr.dbo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cr.dbo.access.USER;

@Repository
public interface Queries extends CrudRepository<USER,Long>{
	@Query(value="select u.id as id,u.username,u.password,u.enabled from [USER] u left join [userxgroup] [upg] on u.id=[upg].id_user left join [group] g on [upg].id_group=g.id where g.name=:prova", nativeQuery=true)
	List<Object[]> findByGroup(@Param("prova")String group);
}
