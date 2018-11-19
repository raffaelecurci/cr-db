package cr.dbo;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import cr.DBApplication;
import cr.annotation.QueueDefinition;
import cr.dbo.ops.BUILD;
import cr.dbo.ops.COMMIT;
import cr.dbo.ops.COMMITTER;
import cr.dbo.ops.FLAW;
import cr.dbo.ops.JENKINS;
import cr.dbo.ops.PROJECT;
import cr.dbo.ops.SECURITYPROJECT;
import cr.dbo.ops.repository.BUILDRepository;
import cr.dbo.ops.repository.COMMITRepository;
import cr.dbo.ops.repository.COMMITTERRepository;
import cr.dbo.ops.repository.FLAWRepository;
import cr.dbo.ops.repository.PROJECTRepository;
import cr.dbo.ops.repository.SECURITYPROJECTRepository;
import cr.dbo.repository.ProjectQueries;
import cr.interf.EncryptedMessage;
import cr.shared.BuildRecord;
import cr.shared.Commit;
import cr.shared.FlawList;
import cr.shared.JenkinsBuildInfo;
import cr.shared.JenkinsJob;
import cr.shared.Project;
import cr.shared.ProjectList;
import cr.shared.ProjectScan;
import cr.shared.Reply;
import cr.shared.SecurityProject;
import cr.shared.SecurityProjectList;

@Component
public class DBManager {
	@Autowired
	private ProjectQueries queries;
	@Autowired
	private PROJECTRepository pROJECTRepository;
	@Autowired
	private COMMITTERRepository cOMMITTERRepository;
	@Autowired
	private COMMITRepository cOMMITRepository;
	@Autowired
	private BUILDRepository bUILDRepository;
	@Autowired
	private SECURITYPROJECTRepository sECURITYPROJECTRepository;
	@Autowired
	private FLAWRepository fLAWRepository; 
	

	private static String encryption = DBApplication.class.getAnnotation(QueueDefinition.class).encryption();

	public EncryptedMessage action(EncryptedMessage message) {
		if (message.getPayloadType().equals("cr.shared.Commit"))
			processCommit(message);
		if (message.getPayloadType().equals("cr.shared.ProjectScan"))
			return processProjectScan(message);
		if (message.getPayloadType().equals("cr.shared.BuildRecord"))
			return addUpdateDeleteBuildRecord(message);
		if (message.getPayloadType().equals("cr.shared.SecurityProject"))
			return listSecurityProject(message);
		if (message.getPayloadType().equals("cr.shared.JenkinsBuildInfo"))
			updateBuildRecordFromJenkins(message);
		if (message.getPayloadType().equals("cr.shared.JenkinsJob"))
			return manageJenkins(message);
		if (message.getPayloadType().equals("cr.shared.FlawList"))
			manageFlawList(message);
		return new Reply().setReply("NOP").toEncryptedMessage(encryption).encodeBase64();
	}

	private EncryptedMessage manageFlawList(EncryptedMessage message) {
		FlawList fl=message.decodeBase64ToObject();
		if(fl.getFlaws().size()>0) {
			COMMIT c=queries.findCommitByHash(fl.getFlaws().get(0).getCommitHash());
			fl.getFlaws().forEach(f->{
				FLAW fd=new FLAW(f);
				fd.setId_commit(c.getId());
				fLAWRepository.save(fd);
			});
		}
		
		return new Reply().setReply("NOP").toEncryptedMessage(encryption).encodeBase64();
	}

	private EncryptedMessage manageJenkins(EncryptedMessage message) {
		JenkinsJob jj=message.decodeBase64ToObject();
		if(jj.getId()==null) {
			JENKINS jenkins=queries.findJenkinsJobBySecurityId(jj.getIdSecurityRepository());
			jj.setId(jenkins.getId());
			jj.setJob(jenkins.getJob());
			jj.setUser(jenkins.getUsername());
			jj.setToken(jenkins.getPassword());
			jj.setUrl(jenkins.getUrl());
			return jj.toEncryptedMessage(encryption).encodeBase64();
		}
		return null;
	}

	private void updateBuildRecordFromJenkins(EncryptedMessage message) {
		JenkinsBuildInfo jpb = message.decodeBase64ToObject();
		BUILD b = queries.findBuildByCommitHash(jpb.getCommit());
		b.setIdJenkinsBuild(jpb.getBuildId());
		b.setStatus(jpb.getStatus());
		if(jpb.getVeracodeScan()!=null) {
			b.setVeracodePackageName(jpb.getVeracodeScan());
			 SECURITYPROJECT sp = sECURITYPROJECTRepository.findById(b.getidSecurityProject()).get();
			 sp.setAvailable(true);
			 sECURITYPROJECTRepository.save(sp);
			 PROJECT p=pROJECTRepository.findById(b.getIdRepository()).get();
			 p.setLastscan(new Date());
			 pROJECTRepository.save(p);
		}
		bUILDRepository.save(b);
		
	}

	private EncryptedMessage listSecurityProject(EncryptedMessage message) {
		ObjectMapper mapper = new ObjectMapper();
		SecurityProject sp = message.decodeBase64ToObject();
		List<SECURITYPROJECT> l = queries.findSecurityProjectByLanguage(sp.getLanguage());
		List<SecurityProject> splist = new LinkedList<SecurityProject>();
		l.forEach(e -> {
			try {
				SecurityProject p = mapper.readValue(e.toJson().toString(), SecurityProject.class);
				splist.add(p);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		return new SecurityProjectList(splist).toEncryptedMessage(encryption).encodeBase64();
	}

	private EncryptedMessage addUpdateDeleteBuildRecord(EncryptedMessage message) {
		BuildRecord br = message.decodeBase64ToObject();
		System.out.println("\n\n"+br.getStatus()+" "+br.getId());
		if (br.getId() == null) {
			BUILD b = new BUILD();
			COMMIT commit = queries.findLastCommitForProject(br.getIdRepository());
			System.out.println("\t"+commit);
			b.setIdCommit(commit.getId());
			b.setStatus(br.getStatus());
			b.setDate(br.getDate());
			b.setIdRepository(br.getIdRepository());
			b.setidSecurityProject(br.getIdSecurityProject());
			b.setStoragefolder(br.getStoragefolder());
			b = bUILDRepository.save(b);// -Dmaven.test.skip=true
			SECURITYPROJECT spr = sECURITYPROJECTRepository.findById(br.getIdSecurityProject()).get();
			PROJECT pr = pROJECTRepository.findById(br.getIdRepository()).get();
			pr.setLanguage(spr.getLanguage());
			pROJECTRepository.save(pr);
			commit.setIdBuildRecord(b.getId());
			cOMMITRepository.save(commit);
			br.setId(b.getId());
			return br.toEncryptedMessage(encryption).encodeBase64();
		} else if (br.getStatus().equals("DELETE")) {
			COMMIT commit = queries.findLastCommitForProject(br.getIdRepository());
			BUILD b = queries.findBuildByCommitHash(commit.getHash());
			bUILDRepository.delete(b);
			return new Reply().setReply("NOP").toEncryptedMessage(encryption).encodeBase64();
		} else if (br.getStatus().equals("BUILDSTARTING")) {
			SECURITYPROJECT sp = sECURITYPROJECTRepository.findById(br.getIdSecurityProject()).get();
			br.getIdSecurityProject();
			queries.markSecurityProjectAsUnavailable(br.getIdSecurityProject());
			System.out.println("Updating security Project "+sp.getUrl()+" as unavailable");
			return new Reply().setReply("Build Started").toEncryptedMessage(encryption).encodeBase64();
		}else
			return null;

	}

	private EncryptedMessage processProjectScan(EncryptedMessage message) {
		ObjectMapper mapper = new ObjectMapper();
		ProjectScan prjscan = message.decodeBase64ToObject();
		List<PROJECT> listp = queries.findProjectForScan(prjscan.getDate());
		List<Project> rplist = new LinkedList<Project>();
		listp.forEach(e -> {
			try {
				Project p = mapper.readValue(e.toJson().toString(), Project.class);
				rplist.add(p);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		return new ProjectList(rplist).toEncryptedMessage(encryption).encodeBase64();
	}

	private synchronized EncryptedMessage processCommit(EncryptedMessage message) {
		Commit commit = message.decodeBase64ToObject();
		System.out.println("processCommit: " + commit);
		
//		queries = cr.BeanUtil.getBean(ProjectQueries.class);
		PROJECT p = queries.findProject(commit.getProjectname(), commit.getUrl());
		if (p == null)
			p = pROJECTRepository.save(new PROJECT("", null, commit.getProjectname(), commit.getUrl()));
		COMMITTER c = queries.findCommitter(commit.getEmail());
		if (c == null)
			c = cOMMITTERRepository
					.save(new COMMITTER(commit.getEmail(), commit.getCommittername(), commit.getUsername()));
		COMMIT cmt = cOMMITRepository.save(new COMMIT(commit.getHash(), c.getId(), p.getId()));

		return cmt.toEncryptedMessage().encodeBase64();
	}
}