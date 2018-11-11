package cr.dbo.repository;

import cr.dbo.ops.COMMIT;
import cr.dbo.repository.annotation.NativeQuery;


public interface ProjectQueries {
	@NativeQuery(query="the query",resultClass=COMMIT.class)
    String doSomething ();
}