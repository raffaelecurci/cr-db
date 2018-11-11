package cr.dbo.repository;

import cr.dbo.ops.COMMITTER;
import cr.dbo.ops.PROJECT;
import cr.dbo.repository.annotation.NativeQuery;


public interface ProjectQueries {
	@NativeQuery(query="select * from [committer] where email=?",resultClass=COMMITTER.class)
    public COMMITTER findCommitter (String email);
	
	@NativeQuery(query="select * from [project] where name=? and url=?",resultClass=PROJECT.class)
    public PROJECT findProject (String name,String url);
}