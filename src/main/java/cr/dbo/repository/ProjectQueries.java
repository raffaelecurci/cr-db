package cr.dbo.repository;

import java.util.Date;
import java.util.List;

import cr.dbo.ops.BUILD;
import cr.dbo.ops.COMMIT;
import cr.dbo.ops.COMMITTER;
import cr.dbo.ops.JENKINS;
import cr.dbo.ops.PROJECT;
import cr.dbo.ops.SECURITYPROJECT;
import cr.dbo.repository.annotation.NativeQuery;


public interface ProjectQueries {
	@NativeQuery(query="select * from [committer] where email=?",resultClass=COMMITTER.class)
    public COMMITTER findCommitter (String email);
	
	@NativeQuery(query="select * from [project] where name=? and url=?",resultClass=PROJECT.class)
    public PROJECT findProject (String name,String url);
	
	@NativeQuery(query="select * from [project] p where (lastscan is null or convert(date,lastscan)<=?) and p.id not in(select id_repository from build where status in('TOBUILD','BUILDSTARTING','BUILDING','PUSHED_TO_VERACODE','SUCCESS','FAILURE','DELETE'))",resultClass=PROJECT.class)
    public List<PROJECT> findProjectForScan (Date d);
	
	@NativeQuery(query="DECLARE @idrepo INTEGER set @idrepo=? select * from [commit] where date=(select max(date) from [commit] where id_repository=@idrepo) and id_repository=@idrepo",resultClass=COMMIT.class)
	public COMMIT findLastCommitForProject(Integer idRepository);

	@NativeQuery(query="select * from securityproject where language=?",resultClass=SECURITYPROJECT.class)
	public List<SECURITYPROJECT> findSecurityProjectByLanguage(String language);
	
	@NativeQuery(query="select * from [commit] where hash=?",resultClass=COMMIT.class)
	public COMMIT findCommitByHash(String hash);
	
	@NativeQuery(query="select b.* from [build] b left join [commit] c on b.id_commit=c.id where c.hash=?",resultClass=BUILD.class)
	public BUILD findBuildByCommitHash(String commit);

	@NativeQuery(query="update securityproject set available=0 where id=?")
	public void markSecurityProjectAsUnavailable(Integer idSecurityProject);

	@NativeQuery(query="select j.* from jenkins j left join securityproject s on j.id=s.id_jenkins where s.id=?",resultClass=JENKINS.class)
	public JENKINS findJenkinsJobBySecurityId(Integer idSecurityRepository);
	

}